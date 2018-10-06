package com.cg.obs.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.User;
import com.cg.obs.exception.InvalidChoiceException;
import com.cg.obs.exception.InvalidCredentialsException;
import com.cg.obs.service.ILoginService;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSServiceFactory;

public class ClientMain {

	private static ILoginService loginService = OBSServiceFactory
			.getLoginService();
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		
		PropertyConfigurator.configure("res/log4j.properties");

		int choice = -1;

		while (choice != 3 /* && loginAttempts<=3 */) {
			System.out
					.println("\n*******Welcome to Online Banking System**********");
			System.out.println("Login As--->");
			System.out.print("[1]Admin [2]Customer [3]Quit >");
			try{
				choice = scan.nextInt();
			}catch(InputMismatchException e){
				scan.next();
				System.err.println("Kindly enter correct option!!!");
			}
			
			

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
				
				int loginChoice=showLoginOptionsForCustomer();
				
				if(loginChoice==1){

					int loginAttempts = 0;
					String customerUserName = null;
					String customerPassword = null;
					System.out.println("User ID :");
					customerUserName = scan.next();
					int customerId = 0;
					boolean userIdValid = false;
					boolean credFlag = false;
					try {
						customerId = Integer.parseInt(customerUserName);
						userIdValid = loginService.validateUserId(customerId);
					} catch (NumberFormatException e1) {
						System.err.println("User ID must be in specified format only");
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
							int user_id = 0;
							try {
								user_id = loginService.getUserLogin(customerId,
										customerPassword);
							} catch (InvalidCredentialsException e) {
								System.err.println(e.getMessage());
							}
							if (user_id != 0) {
								UserClient userClient = new UserClient();
								userClient.clientConsole(user_id);
								System.out.println("client login done");
							}
						} else if (loginAttempts == 3) {
							boolean success = loginService
									.lockUserAccount(customerId);
							System.err
									.println("Invalid Login attempts exceeded!!!.Your account has been locked");
						}
				}
				

				}else if(loginChoice==2){
					
					System.out.println("Enter customer ID");
					
					int id=scan.nextInt();
					try{
						User user=loginService.forgotPassword(id);
						if(user!=null){
							System.out.println("Your security question is :");
							System.out.println(user.getSecretQuestion());
							System.out.println("Enter answer:");
							String secretAnswer=scan.next();
							if(user.getSecretAnswer().equals(secretAnswer)){
								System.out.println("Answer validation successfull");
								String newPassword="#sbq500";
								boolean success=loginService.setOneTimePassword(newPassword,id);
								if(success){
									System.out.println("\nYour one time login password is: #sbq500. Kindly login using this passoword and create new password for future use.");
								}
								else{
									System.err.println("\nError Occured ! Please try again");
								}
					
							}else{
								System.err.println("\nInvalid answer..Try again");
							}
						}else{
							System.err.println("\nUser ID does not exist");
						}
					}catch(NullPointerException ne){
						System.err.println(ne.getMessage());
					}
				}
			}

		}
		scan.close();
		System.out.println("Program Terminated");
	}

	private static int showLoginOptionsForCustomer() {
		int choice=0;
		System.out.println("**************Customer Login*************");
		System.out.println("1. Proceed to Login");
		System.out.println("2. Forgot Password");
		try {
			 choice= scan.nextInt();
			if (choice < 1 || choice > 2) {
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
