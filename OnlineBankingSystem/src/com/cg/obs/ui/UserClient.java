package com.cg.obs.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.util.Messages;

public class UserClient {

	public static void main(String[] args) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (true) {
			choice = getChoice(scan);
			switch (choice) {
			case 1:

				break;
			case 2:
				break;
			case 3:
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

}
