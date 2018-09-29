package com.cg.obs.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.exception.InvalidPasswordEntered;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.service.ICustomerService;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSServiceFactory;

public class UserClient {

	private static ICustomerService cService = OBSServiceFactory
			.getCustomerBean();
	public static String[] ar = { "1001", "1002", "1003" };

	public static int countPassTries = 0;

	public static void main(String[] ar) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			choice = getChoice(scan);
			switch (choice) {
			case 1:
				System.out.println("Enter your customer id:");
				int id = scan.nextInt();

				System.out.println("Displaying Existing Details:");
				Customer customer = cService.getCustomerDetails(id);
				System.out.println(customer);
				System.out.println("Enter new Mobile Number:");
				long mobile = scan.nextLong();
				System.out.println("Enter new Address:");
				String address = scan.next();

				try {
					cService.validate(mobile, address);
					customer.setMobile(mobile);
					customer.setAddress(address);
					boolean result = cService.updateCustomerDetails(customer);
					if (result)
						System.out
								.println("Your Details have been successfully updated!");
					else
						System.out
								.println("Sorry!, Your details could not be updated. Please try again.");

				} catch (InvalidDetailsEntered e) {
					if (e.getMessage().equals("mobile")) {
						System.err.println(Messages.INCORRECT_MOBILE_NUMBER);
						scan.next();
					} else {
						System.err.println(Messages.INCORRECT_CUSTOMER_ADDRESS);
						scan.next();
					}
				} catch (UpdateCustomerException e) {
					System.err.println(Messages.UPDATE_CUSTOMER_FAILED);
					scan.next();
				}

				break;
			case 2:
				
				while (true) {
					if(doCountCheck()) break;
					
					String oldPass = getOldPass(scan);
					boolean validPass = cService.checkOldPass(oldPass, ar[0]);
					
					if(!validPass){
						System.err.println("Your old-password is invalid! Please Try Again");
						scan.next();
						countPassTries++;
						continue;
					}
					
					String[] newPass = getNewPass(scan).split(" ");
					
					/*if(!validNewPass(newPass[0]),newPass[1]){
						
					}*/
					System.out.println("Your password has been successfully updated!");
				}	//("You have exceeded the maximum number of tries!");
				break;
			case 3:
				System.out
						.println("Thank you for using ONLINE BANKING SYSTEM!!!");
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
		System.out.println("3. Exit");
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
		String newPass1 = scan.nextLine();
		System.out.println("Enter new Password again:");
		String newPass2 = scan.nextLine();
		String res = newPass1 + " " + newPass2;
		return res;
	}

	private static String getOldPass(Scanner scan) {
		System.out.println("Enter your old Password:");
		return scan.nextLine();
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
