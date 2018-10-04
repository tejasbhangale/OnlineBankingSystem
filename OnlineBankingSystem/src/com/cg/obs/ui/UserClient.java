package com.cg.obs.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.InvalidChoiceException;
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

	public static void main(String[] args) {
		UserClient user = new UserClient();
		user.clientConsole(1001);
	}

	public void clientConsole(int ar) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			choice = getChoice(scan);
			switch (choice) {
			case 1:// Update Mobile/Address

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
			case 2:// ChangePassword

				/*
				 * User is given 3 tries to enter 'old password' and 3 more
				 * tries to enter valid 'new Password'
				 */
				countPassTries = 0;
				boolean validPass = false;
				while (!validPass && countPassTries < 3) {
					System.out.println("In");
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

			case 3://
				int requestNumber = cService.requestChequeBook(ar);
				if (requestNumber != 0) {
					System.out.println(Messages.CHEQUEBOOK_SUCCESS);
					System.out.println("Your service request number is: " + requestNumber);
				} else {
					System.out.println(Messages.SERVICE_REQUEST_FAILED);
				}
				break;
			case 4:
				// LogOut
				System.out.println(Messages.EXIT_MESSAGE);
				System.exit(1);
				break;
			default:
				System.out.println("Invalid choice, please try again!");
				break;

			}
		}

	}

	private static int getChoice(Scanner scan) {
		int choice = 0;
		System.out
				.println("**************WELCOME TO ONLINE BANKING SYSTEM**************");
		System.out.println("Choose Option:");
		System.out.println("1. Change address/mobile number");
		System.out.println("2. Change password");
		System.out.println("3. Request Cheque Book");
		System.out.println("4. Exit");
		System.out
				.println("************************************************************");

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
