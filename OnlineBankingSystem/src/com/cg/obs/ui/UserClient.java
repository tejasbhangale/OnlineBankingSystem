package com.cg.obs.ui;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.CompleteProfileException;
import com.cg.obs.exception.IncorrectPasswordException;
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

	public static int countPassTries = 0;
	static UserClient user = new UserClient();
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		UserClient user = new UserClient();
		user.clientConsole(135);
		System.out.println("I'm Out");
	}

	public void clientConsole(int userId) {

		Scanner scan = new Scanner(System.in);
		if (cService.isFirstTimeUser(userId)) {
			if (doNewUserActivity(scan, userId)) {
				System.out.println("Thank you for completing your profile!");
				doUserActivity(scan, userId);
			}
			else{
				System.out.println("Sorry! You did not complete your profile.");
				System.out.println("You will be logged out! Please try again.");
			}
		} else {
			doUserActivity(scan, userId);
		}

	}
	
	public void doUserActivity(Scanner scan, int userId){
		int choice = 0;
		while (choice != 7) {
			choice = getChoice(scan);
			switch (choice) {
			case 1:// mini/detailed statement
				doDisplayStatement(userId);
				break;
			case 2:// Update Mobile/Address
				doDetailsUpdate(scan, userId);
				break;
			case 3:// Checkbook request
				doChequebookRequest(scan, userId);
				break;
			case 4:// track service
				doTrackService(scan, userId);
				break;
			case 5:// fund transfer
				fundTransfer(scan,userId);
				break;
			case 6:// ChangePassword
				doPasswordUpdate(scan, userId);
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
	
	
	private void doDisplayStatement(int userId) {
		boolean status = true;

		while (status) {
			System.out.println("1. Mini Statement");
			System.out.println("2. Detailed Statement");
			System.out.println("3. Exit");
			System.out.println("Enter your choice");
			String check = sc.next();

			switch (check) {
			case "1":

				user.getMiniStatement(userId);

				break;
			case "2":                                                                                                                                                                                                

				user.getDetailedStatement(userId);

				break;
			case "3":

				status = false;

				break;
			default:
				System.out.println("Enter a valid option");

				break;
			}

		}
		
	}

	private boolean doNewUserActivity(Scanner scan, int userId) {
		ArrayList<String> userData = doInputGet(scan);
		try {
			cService.validateUserData(userData, userId);
			System.out.println("Data Validated");
			cService.completeProfile(userData, userId);
			return true;
		} catch (InvalidDetailsEntered e) {
			System.out.println(e.getMessage());
		} catch (CompleteProfileException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	private ArrayList<String> doInputGet(Scanner scan) {
		ArrayList<String> inpData = new ArrayList<String>();

		System.out.println("It seems it is your first time here.");
		System.out
				.println("Please complete your profile to enhance your experience here.");
		System.out.println("Here's a few things we'ed like to ask");

		System.out.println("\nFirst, we need to change your old password!");
		System.out.println("So,Enter your old pass to verify your identity:");

		inpData.add(scan.next());

		System.out
				.println("Great!, Now its time to set up your transaction password, enter it:");
		inpData.add(scan.next());

		System.out
				.println("You're doing great! Enter your secret question");
		System.out.println("(Needed in-case you forget your password and need to change it):");
		scan.nextLine();
		inpData.add(scan.nextLine());
		System.out.println("Lastly, enter the answer to this question:");
		inpData.add(scan.nextLine());

		System.out.println(inpData.toString());
		return inpData;
	}

	private void doPasswordUpdate(Scanner scan, int userId) {
		String[] pass = doPassInputAndValidate(scan, userId);
		if (pass == null) {
			System.err.println("Sorry, your request could not be processed!");
		} else {
			try {
				cService.updatePassword(pass[1], userId);
				System.out.println("Password Update Succesfully Completed.\n");
			} catch (PasswordUpdateException e) {
				System.err.println("Error adding password!");
			}
		}
	}

	private String[] doPassInputAndValidate(Scanner scan, int userId) {
		countPassTries = 0;

		while (countPassTries < 3) {
			String[] pass = getPassInput(scan);
			String[] validatedPass;
			try {
				validatedPass = cService.checkPass(pass, userId);
				return validatedPass;
			} catch (IncorrectPasswordException e) {
				countPassTries++;
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Maximum Tries Exceeded. Try Again.");
		return null;
	}

	private String[] getPassInput(Scanner scan) {
		System.out.println("Please enter your old password: ");
		String[] res = { "", "", "" };
		res[0] = scan.next();
		System.out.println("Enter your new password:");
		res[1] = scan.next();
		System.out.println("Enter new password again:");
		res[2] = scan.next();
		return res;
	}

	private void doChequebookRequest(Scanner scan, int userId) {
		System.out.println("Select Account to request ChequeBook for it:");

		HashMap<Integer, Integer> mapAcc = printAndGetAllAccounts(userId);

		int requestNumber = cService.requestChequeBook(mapAcc.get(scan
				.nextInt()));

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

	private void doTrackService(Scanner scan, int userId) {

		int sNum = getServiceChoice(scan);
		switch (sNum) {
		case 1:
			System.out.println("Enter Service Request Number:");
			ServiceTracker sTrack = cService.getRequestStatus(scan.nextInt(),
					userId);
			if (sTrack != null)
				doSuccessRequest(sTrack);
			else
				doFailureRequest();
			break;
		case 2:
			System.out.println("Select Account to Track Service for it:");
			HashMap<Integer, Integer> mapAcc = printAndGetAllAccounts(userId);
			ArrayList<ServiceTracker> requestList = cService
					.getAllRequestStatus(mapAcc.get(scan.nextInt()));
			if (requestList.isEmpty() | requestList == null)
				doFailureAllRequests();
			else
				doSuccessAllRequests(requestList);
			break;
		case 3:
			System.out.println("Going Back to your Home Page");
			break;
		default:
			System.out.println("You have selected an incorrect option");
			break;
		}

	}

	private HashMap<Integer, Integer> printAndGetAllAccounts(int userId) {
		ArrayList<Integer> accNums = cService.getAllAccounts(userId);
		HashMap<Integer, Integer> mapAcc = new HashMap<Integer, Integer>();
		AtomicInteger index = new AtomicInteger(1);
		accNums.forEach((acc) -> {
			int ind = index.getAndIncrement();
			mapAcc.put(ind, acc);
			System.out.println(ind + ". " + acc);
		});
		System.out.println("\nEnter Option:");
		return mapAcc;
	}

	private void getMiniStatement(int ar) {

		List<Transactions> transaction;
		try {
			transaction = cService.getMiniStatement(ar);
			if (transaction == null) {
				System.out.println("No Transaction found for given Account");
			} else {
				for (Transactions transactions : transaction) {
					System.out.println(transactions);
				}
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
		System.out.println("Request Failed");
	}

	private void doSuccessAllRequests(ArrayList<ServiceTracker> requestList) {
		requestList.forEach((a) -> {
			System.out.println(a);
		});
	}

	private void doFailureRequest() {
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
