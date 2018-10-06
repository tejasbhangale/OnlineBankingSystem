package com.cg.obs.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.service.ICustomerService;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSServiceFactory;

public class UserClient {

	private static ICustomerService cService = OBSServiceFactory
			.getCustomerBean();
	// public static int ar = 1001;

	public static int countPassTries = 0;
	static UserClient user = new UserClient();
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		UserClient user = new UserClient();
		user.clientConsole(1002);
	}

	public void clientConsole(int ar) {

		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			if (choice == 7)
				break;
			choice = getChoice(scan);
			switch (choice) {
			case 1:// mini/detailed statement

				boolean status = true;

				while (status) {
					System.out.println("1. Mini Statement");
					System.out.println("2. Detailed Statement");
					System.out.println("3. Exit");
					System.out.println("Enter your choice");
					String check = sc.next();

					switch (check) {
					case "1":

						user.getMiniStatement(ar);

						break;
					case "2":

						user.getDetailedStatement(ar);

						break;
					case "3":

						status = false;

						break;
					default:
						System.out.println("Enter a valid option");

						break;
					}

				}

				break;
			case 2:// Update Mobile/Address
				doDetailsUpdate(scan, ar);
				break;
			case 3:// Checkbook request
				doChequebookRequest(scan, ar);
				break;
			case 4:// track service
				doTrackService(scan, ar);
				break;
			case 5:// fund transfer
				fundTransfer(scan,ar);
				break;
			case 6:// ChangePassword
				doPasswordUpdate(scan, ar);
				break;
			case 7:
				// LogOut
				System.out.println(Messages.EXIT_MESSAGE);
				break;
			default:
				System.out.println("Invalid choice, please try again!");
				break;

			}
		}

	}

	private void doChequebookRequest(Scanner scan, int ar) {
		int requestNumber = cService.requestChequeBook(ar);
		if (requestNumber != 0) {
			System.out.println(Messages.CHEQUEBOOK_SUCCESS);
			System.out.println("Your service request number is: "
					+ requestNumber);
		} else {
			System.out.println(Messages.SERVICE_REQUEST_FAILED);
		}
	}

	private void doDetailsUpdate(Scanner scan, int ar) {
		/*
		 * Displaying Existing Details
		 */
		Customer customer = cService.getCustomerDetails(ar); 
		System.out.println("Displaying Existing Details:");
		System.out.println(customer);

		/*
		 * Functionality to be added later System.out.println(
		 * "To keep existing data, leave the corresponding field empty" );
		 */
		try {
			System.out.println("Enter new Mobile Number:");
			long mobile = scan.nextLong();
			System.out.println("Enter new Address:");
			String address = scan.next();

			/*
			 * Validating Entered Details and if validated, Updating
			 */
			cService.validate(mobile, address);
			customer.setMobile(mobile);
			customer.setAddress(address);
			boolean result = cService.updateCustomerDetails(customer);
			if (result)
				System.out.println(Messages.CUSTOMER_UPDATE_SUCCESS);
			else
				System.out.println(Messages.CUSTOMER_UPDATE_FAILED_CLIENT);

		} catch (InvalidDetailsEntered e) {
			System.out.println(e.getMessage());
		} catch (UpdateCustomerException e) {
			System.err.println(Messages.CUSTOMER_UPDATE_FAILED_DAO);
		} catch (InputMismatchException e1) {
			System.err.println(Messages.INVALID_MOBILE_FORMAT);
			scan.next();
		}

	}

	private void doPasswordUpdate(Scanner scan, int ar) {
		/*
		 * User is given 3 tries to enter 'old password' and 3 more tries to
		 * enter valid 'new Password'
		 */
		countPassTries = 0;
		boolean validPass = false;
		while (!validPass && countPassTries < 3) {
			String oldPass = getOldPass(scan);
			validPass = cService.checkOldPass(oldPass, ar);
			if (validPass)
				break;
			System.err.println(Messages.INVALID_OLD_PASS);
			countPassTries++;
		}

		boolean validNewPass = false;
		countPassTries = 0;
		while (!validNewPass && countPassTries < 3) {
			String newPass = getNewPass(scan);
			validNewPass = cService.checkNewPass(newPass);

			if (validNewPass) {
				try {
					cService.updatePassword(newPass, ar);
					System.out.println(Messages.PASSWORD_UPDATE_SUCCESS);
					break;
				} catch (PasswordUpdateException e) {
					System.err.println(Messages.PASSWORD_UPDATE_FAILED);
				}
			} else {
				countPassTries++;
				if (countPassTries == 3) {
					System.err.println(Messages.MAXIMUM_NEWPASS_TRIES);
				}
			}

		}

	}

	private void doTrackService(Scanner scan, int ar) {
		int sNum = getServiceChoice(scan);
		switch (sNum) {
		case 1:
			System.out.println("Enter Service Request Number:");
			int accNum = ar;
			ServiceTracker sTrack = cService.getRequestStatus(scan.nextInt(),
					accNum);
			if (sTrack != null)
				doSuccessRequest(sTrack);
			else
				doFailureRequest();
			break;
		case 2:
			System.out.println("Enter Account Number:");
			int accNumber = scan.nextInt();
			if (accNumber == ar) {
				ArrayList<ServiceTracker> requestList = cService
						.getAllRequestStatus(accNumber);
				if (requestList.isEmpty() | requestList == null)
					doFailureAllRequests();
				else
					doSuccessAllRequests(requestList);
			} else {
				System.err.println("Wrong account number entered.");
				break;
			}
			break;
		case 3:
			System.out.println("Going Back to your Home Page");
			break;
		default:
			System.out.println("You have selected an incorrect option");
			break;
		}

	}

	private void getMiniStatement(int ar) {

		List<Transactions> transaction;
		try {
			transaction = cService.getMiniStatement(ar);
			if (transaction == null) {
				System.out.println("No Transaction found for given Account");
			} else {
				System.out.println(transaction);
			}
		} catch (JDBCConnectionError e) {
			System.out.println(e.getMessage());
		}
	}

	private void getDetailedStatement(int ar) {

		String sDate;
		String eDate;

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		List<Transactions> list = new ArrayList<Transactions>();

		System.out.println("Enter the starting date (dd/MM/yyyy) : ");
		sDate = sc.next();
		System.out.println("Enter the end date (dd/MM/yyyy) : ");
		eDate = sc.next();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {

			Date stDate = format.parse(sDate);
			startDate = new java.sql.Date(stDate.getTime());

			Date edDate = format.parse(eDate);
			endDate = new java.sql.Date(edDate.getTime());

			if (startDate.before(endDate)) {

				list = cService.getDetailedStatement(ar, startDate, endDate);

				if (list.size() == 0) {

					System.out.println("No Transaction exist for given dates");

				} else {

					for (Transactions tra : list) {

						System.out.println(tra.toString());

					}
				}
			} else {
				System.err
						.println("Start date can not be greater than end date");
			}

		} catch (ParseException e) {

			System.err.println("Enter valid date format");

		} catch (JDBCConnectionError e) {

			System.out.println(e.getMessage());

		}

	}

	private void doFailureAllRequests() {
		// TODO Auto-generated method stub
		System.out.println("Request Failed");
	}

	private void doSuccessAllRequests(ArrayList<ServiceTracker> requestList) {
		requestList.forEach((a) -> {
			System.out.println(a);
		});
	}

	private void doFailureRequest() {
		// TODO Auto-generated method stub
		System.out.println("Request Failed");
	}

	private void doSuccessRequest(ServiceTracker sTrack) {
		System.out.println(sTrack);
	}

	private static int getChoice(Scanner scan) {
		int choice = 0;
		System.out
				.println("**************WELCOME TO ONLINE BANKING SYSTEM**************");
		System.out.println("Choose Option:");
		System.out.println("");
		System.out.println("1. View Mini/Detailed Statement");
		System.out.println("2. Change address/mobile number");
		System.out.println("3. Request Cheque Book");
		System.out.println("4. Track Service Request");
		System.out.println("5. Fund Transfer");
		System.out.println("6. Change Password");
		System.out.println("7. Exit");
		System.out
				.println("************************************************************");

		try {
			choice = scan.nextInt();
			if (choice < 1 || choice > 7) {
				throw new InvalidChoiceException(Messages.INCORRECT_CHOICE);
			}
		} catch (InputMismatchException e) {
			System.err.println(Messages.INCORRECT_INPUT_TYPE);
			scan.next();
		} catch (InvalidChoiceException e) {
			System.err.println(e.getMessage());
		}
		return choice;
	}

	private static int getServiceChoice(Scanner scan) {
		int choice = 0;
		System.out.println("***TRACK SERVICE REQUEST****");
		System.out.println("Choose Option:");
		System.out.println("");
		System.out.println("1. Enter Service Number");
		System.out.println("2. Enter Account Number");
		System.out.println("3. Exit");
		System.out.println("****************************");

		try {
			choice = scan.nextInt();
			if (choice < 1 || choice > 3) {
				throw new InvalidChoiceException(Messages.INCORRECT_CHOICE);
			}
		} catch (InputMismatchException e) {
			System.err.println(Messages.INCORRECT_INPUT_TYPE);
			scan.next();
		} catch (InvalidChoiceException e) {
			System.err.println(e.getMessage());
		}
		return choice;
	}

	private static String getNewPass(Scanner scan) {
		System.out.println("Enter new Password:");
		String newPass1 = scan.next();
		System.out.println("Enter new Password again:");
		String newPass2 = scan.next();
		String res = newPass1 + " " + newPass2;
		return res;
	}

	private static String getOldPass(Scanner scan) {
		System.out.println("Enter your old Password:");
		String pass = scan.next();
		return pass;
	}
	private static void fundTransfer(Scanner scan, long userId){
		int choice = 0,count=0,transactionId;
		long fromaccount = 0,toaccount = 0;
		double transferAmount=0;
		boolean FTFlag=true;
		userId=120;
		while(FTFlag){
		System.out.println("*******Funds Transfer*******");
		System.out.println("1. Your Own Bank Account across India");
		System.out.println("2. Other  account of same bank across india");
		System.out.println("3. Manage Payee");
		System.out.println("4. Go back");
		System.out.println("****************************");
		try {
			choice = scan.nextInt();
			if (choice < 1 || choice > 3) {
				throw new InvalidChoiceException(Messages.INCORRECT_CHOICE);
			}
		} catch (InputMismatchException e) {
			System.err.println(Messages.INCORRECT_INPUT_TYPE);
			scan.next();
		} catch (InvalidChoiceException e) {
			System.err.println(e.getMessage());
		}
		
		switch(choice){
		case 1://Transfer to own accounts
				List<Integer> selfaccounts=cService.getAccountList(userId);
				System.out.println("	Sr.No	Account_Number");
				count= selfaccounts.size();
				for(int index=0;index<count;index++){
					
					System.out.println("	"+(index+1)+".	"+selfaccounts.get(index));
				}
				try{
				System.out.println("Enter the Sr.no of account to transfer funds from");
				fromaccount=selfaccounts.get(scan.nextInt()-1);
				System.out.println("Enter the Sr.no of account to transfer funds from");
				toaccount=selfaccounts.get(scan.nextInt());
				System.out.println("Enter Amount to be transferred:");
				transferAmount=scan.nextDouble();
				
				
				
				if(fromaccount==toaccount){
					System.err.println("Same account has been selected");
					
				}
				else if(cService.checkfunds(fromaccount,transferAmount)){
					if(verifyTransactionPassword(scan,userId)){
						transactionId=cService.transferfunds(fromaccount,toaccount,transferAmount);
						System.out.println("Funds Transfer is Success!!! Transaction Id is :"+transactionId);
					}
					else{
						System.err.println("Transaction Authentication Failure!!!");
					}
					
				}
				else{
					System.err.println("Insufficient funds to transfer");
				}
				} catch(InputMismatchException e){
					scan.next();
					System.err.println("Please enter in correct format");
				} catch (IndexOutOfBoundsException e){
					System.err.println("Please select correct option");
				}
			break;
		case 2:
			List<Payee> payeeList=cService.getPayeeList(userId);
//			System.out.println("payeelist:"+payeeList);
			if(payeeList.size()>0){
				List<Integer> selfaccountlist=cService.getAccountList(userId);
				System.out.println("Your account list");
				System.out.println("	Sr.No	Account_Number");
				count= selfaccountlist.size();
				for(int index=0;index<count;index++){
					System.out.println("	"+(index+1)+".	"+selfaccountlist.get(index));
				}
				try{
				System.out.println("Enter the Sr.no of account to transfer funds from");
				fromaccount=selfaccountlist.get((scan.nextInt()-1));
				System.out.println("fromaccount- "+fromaccount);
				
				
				System.out.println("Payee account list");
				count= payeeList.size();
				for(int index=0;index<count;index++){
					System.out.println("	"+(index+1)+". "+payeeList.get(index).getPayeeAccountId()+"	"+payeeList.get(index).getNickName());
				}
				System.out.println("Enter the Sr.no of account to transfer funds from");
				toaccount=payeeList.get((scan.nextInt()-1)).getPayeeAccountId();
				System.out.println("toaccount- "+toaccount);
				System.out.println("Enter Amount to be transferred:");
				transferAmount=scan.nextDouble();
				
				
				
				
				if(cService.checkfunds(fromaccount,transferAmount)){
					if(verifyTransactionPassword(scan,userId)){
						transactionId=cService.transferfunds(fromaccount,toaccount,transferAmount);
						System.out.println("Funds Transfer is Success!!! Transaction Id is :"+transactionId);
					}
					else{
						System.err.println("Transaction Authentication Failure!!!");
					}
				}
				else{
					System.out.println("Insufficient funds to transfer");
				}
				}catch(InputMismatchException e){
					scan.next();
					System.err.println("Please enter in correct format");
				}catch (IndexOutOfBoundsException e){
					System.err.println("Please select correct option");
				}
			}
			else
			{
				System.err.println("No Payee added!!! Kindly add Payee first.");
				managePayee(scan,userId);
			}
			
			break;
		case 3:
			managePayee(scan,userId);
			break;
		case 4:
			FTFlag=false;
			break;
		}
	}
	}
	private static boolean verifyTransactionPassword(Scanner scan,long userId){
		long verifyId;
		String verifyPass;
		System.out.println("*****Fund Transfer Authentication*****");
		System.out.println("Enter the User ID");
		verifyId=scan.nextLong();
		System.out.println("Enter the Transaction Password");
		verifyPass=scan.next();
		if(cService.transactionAuthentication(userId,verifyId,verifyPass)){
			return true;
		}else
			return false;
	}
	private static void managePayee(Scanner scan, long userId){
		System.out.println("Add Payee");
		try{
		System.out.println("Enter the Payee Account");
		int payeeAccountId1 = Integer.parseInt(scan.next());
		System.out.println("Confirm the Payee Account");
		int payeeAccountId2=Integer.parseInt(scan.next());
		
		if(payeeAccountId1==payeeAccountId2){
			System.out.println("Enter the nick name for the payee:");
			scan.nextLine();
			
			String payeeNickname=scan.nextLine();
			Payee payee = new Payee(userId,payeeAccountId2,payeeNickname);
			
			if(cService.addPayee(payee)){
				System.out.println("Payee with account ID: "+payeeAccountId2+" with nick name as "+payeeNickname+" is added");
			}
			else{
				System.err.println("Payee already exist");
			}
			
		}else{
			System.err.println("Mismatch in entered account IDs!!");
		}
		
		}catch(OnlineBankingException e){
			System.err.println(e.getMessage());
		}catch(NumberFormatException e){
			System.err.println("Enter the account Id correctly ");
		}
	}
}
