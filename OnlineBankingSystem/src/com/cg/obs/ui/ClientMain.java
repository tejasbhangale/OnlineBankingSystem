package com.cg.obs.ui;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.exception.InvalidCredentialsException;
import com.cg.obs.service.ILoginService;
import com.cg.obs.util.OBSServiceFactory;

public class ClientMain {

	private static ILoginService loginService = OBSServiceFactory
			.getLoginService();

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		PropertyConfigurator.configure("res/log4j.properties");

		int choice = -1;

		while (choice != 3 /* && loginAttempts<=3 */) {
			System.out
					.println("*******Welcome to Online Banking System**********");
			System.out.println("Login As--->");
			System.out.print("[1]Admin [2]Customer [3]Quit >");
			choice = scan.nextInt();

			if (choice == 1) {
				System.out.print("UserName? ");
				String adminUserName = scan.next();
				System.out.print("Password? ");
				String adminPassword = scan.next();

				try {
					boolean success = loginService.getAdminLogin(adminUserName,
							adminPassword);

					if (success) {
						System.out.println("successfully logged in");
						AdminConsole admin = new AdminConsole();
						admin.adminConsole();
					}

				} catch (InvalidCredentialsException e) {
					System.err.println(e.getMessage());
				}

			} else if (choice == 2) {
				int loginAttempts = 0;
				String customerUserName = null;
				String customerPassword = null;
				System.out.println("User ID :");
				customerUserName = scan.next();
				int customerId = 0;
				boolean userIdValid = false;
				boolean userValid = false;
				boolean credFlag = false;
				try {
					customerId = Integer.parseInt(customerUserName);
					userIdValid = loginService.validateUserId(customerId);
				} catch (NumberFormatException e1) {
					System.err.println("User ID must be in int only");
				} catch (InvalidCredentialsException e1) {

					System.err.println(e1.getMessage());
				}
				if (userIdValid) {
					while (loginAttempts < 3 && !credFlag) {

						System.out.println("Password: ");
						customerPassword = scan.next();
						loginAttempts++;
						try {
							credFlag = loginService.validatePassword(
									customerId, customerPassword);
						} catch (InvalidCredentialsException e) {
							System.err.println(e.getMessage());
						}

					}
					if (credFlag && userIdValid) {
						int account_id = 0;
						try {
							account_id = loginService.getUserLogin(customerId,
									customerPassword);
						} catch (InvalidCredentialsException e) {
							System.err.println(e.getMessage());
						}
						if (account_id != 0) {
							UserClient userClient = new UserClient();
							userClient.clientConsole(account_id);
							System.out.println("client login done");
						}
					} else if (loginAttempts == 3) {
						boolean success = loginService
								.lockUserAccount(customerId);
						System.err
								.println("Invalid Login attempts exceeded!!!.Your account has been locked");
					}

				}
			}

		}
		scan.close();
		System.out.println("Program Terminated");
	}

}
