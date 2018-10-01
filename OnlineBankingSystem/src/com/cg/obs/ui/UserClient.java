
package com.cg.obs.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.service.ICustomerService;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSServiceFactory;

public class UserClient {

	private static ICustomerService cService = OBSServiceFactory.getCustomerBean();
	public static int ar = 1001;

	public static int countPassTries = 0;

	public void clientConsole(){
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			choice = getChoice(scan);
			switch (choice) {
			case 1:
				System.out.println("Enter your customer id:");
				int id;
				try {
					id = scan.nextInt();
				} catch (InputMismatchException e2) {
					System.err.println(Messages.INVALID_ID_FORMAT);
					scan.next();
					continue;
				}

				System.out.println("Displaying Existing Details:");
				Customer customer = cService.getCustomerDetails(id);
				if (customer == null) {
					System.out.println("No user with entered ID found. Try again!");
					break;
				}

				System.out.println(customer);

				try {
					System.out.println("Enter new Mobile Number:");
					long mobile = scan.nextLong();
					System.out.println("Enter new Address:");
					String address = scan.next();

					cService.validate(mobile, address);
					customer.setMobile(mobile);
					customer.setAddress(address);
					boolean result = cService.updateCustomerDetails(customer);
					if (result)
						System.out.println("Your Details have been successfully updated!");
					else
						System.out.println("Sorry!, Your details could not be updated. Please try again.");

				} catch (InvalidDetailsEntered e) {
					System.out.println(e.getMessage());
				} catch (UpdateCustomerException e) {
					System.err.println(Messages.UPDATE_CUSTOMER_FAILED);
				} catch (InputMismatchException e1) {
					System.err.println(Messages.INVALID_MOBILE_FORMAT);
					scan.next();
				}

				break;
			case 2:
				countPassTries = 0;
				boolean validPass = false;
				while (!validPass) {
					if (!doCountCheck())
						break;
					String oldPass = getOldPass(scan);
					validPass = cService.checkOldPass(oldPass, ar);
					if (validPass)
						break;
					System.err.println(Messages.INVALID_OLD_PASS);
					countPassTries++;
				}

				if (!doCountCheck())
					break;

				boolean validNewPass = false;
				countPassTries = 0;
				while (!validNewPass) {
					if (!doCountCheck())
						break;

					String newPass = getNewPass(scan);
					validNewPass = cService.checkNewPass(newPass);

					if (validNewPass) {
						try {
							cService.updatePassword(newPass, ar);
							System.out.println("Your password has been successfully updated!");
							break;
						} catch (PasswordUpdateException e) {
							System.err.println(Messages.PASSWORD_UPDATE_FAILED);
						}
					} else {
						countPassTries++;
					}

				}
				break;
			case 3:
				System.out.println("Thank you for using ONLINE BANKING SYSTEM!!!");
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
		System.out.println("**************WELCOME TO ONLINE BANKING SYSTEM**************");
		System.out.println("Choose Option:");
		System.out.println("1. Change address/mobile number");
		System.out.println("2. Change password");
		System.out.println("3. Exit");
		System.out.println("************************************************************");

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

	private static boolean doCountCheck() {
		if (countPassTries >= 3) {
			System.err.println("Maximum tries exceeded! Try Again.");
			return false;
		} else {
			return true;
		}
	}

}

