package com.cg.obs.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.exception.InvalidDetailsEntered;
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
		
		user.clientConsole(1001);
	}

	public void clientConsole(int ar) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			if(choice==7) break;
			choice = getChoice(scan);
			switch (choice) {
			case 1:// mini/detailed statement
				
				boolean status = true;
				
				while(status)
				{
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
					
					status= false;
					
					break;
				default:
					System.out.println("Enter a valid option");
					
					break;
				}
				
				
				}
				
				
				break;
			case 2:// Update Mobile/Address

				/*
				 * Displaying Existing Details
				 */
				Customer customer = cService.getCustomerDetails(ar);
				System.out.println("Displaying Existing Details:");
				System.out.println(customer);

				/*
				 * Functionality to be added later System.out.println(
				 * "To keep existing data, leave the corresponding field empty"
				 * );
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
						System.out
								.println(Messages.CUSTOMER_UPDATE_FAILED_CLIENT);

				} catch (InvalidDetailsEntered e) {
					System.out.println(e.getMessage());
				} catch (UpdateCustomerException e) {
					System.err.println(Messages.CUSTOMER_UPDATE_FAILED_DAO);
				} catch (InputMismatchException e1) {
					System.err.println(Messages.INVALID_MOBILE_FORMAT);
					scan.next();
				}

				break;

			case 3://
				int requestNumber = cService.requestChequeBook(ar);
				if (requestNumber != 0) {
					System.out.println(Messages.CHEQUEBOOK_SUCCESS);
					System.out.println("Your service request number is: "
							+ requestNumber);
				} else {
					System.out.println(Messages.SERVICE_REQUEST_FAILED);
				}
				break;
			case 4:// track service
				int sNum = getServiceChoice(scan);
				switch (sNum) {
				case 1:
					System.out.println("Enter Service Request Number:");
					int accNum = ar;
					ServiceTracker sTrack = cService.getRequestStatus(
							scan.nextInt(), accNum);
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
						if (requestList.isEmpty() | requestList==null)
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
				break;
			case 5:// fund transfer
				break;
			case 6:// ChangePassword

				/*
				 * User is given 3 tries to enter 'old password' and 3 more
				 * tries to enter valid 'new Password'
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
							System.out
									.println(Messages.PASSWORD_UPDATE_SUCCESS);
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



	
	
	private void getMiniStatement(int ar) {
		
		List<Transactions> transaction = cService.getMiniStatement(ar);
		
		if(transaction==null)
		{
			System.out.println("No Transaction found for given Account");
		}
		else
		{
		System.out.println(transaction);
		}
	}

	
     
	
	private void getDetailedStatement(int ar) {
		
		
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

}
