package com.cg.obs.ui;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.exception.InvalidCredentialsException;
import com.cg.obs.service.ILoginService;
import com.cg.obs.util.OBSServiceFactory;



public class ClientMain {
	
	private static ILoginService loginService=OBSServiceFactory.getLoginService();
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		PropertyConfigurator.configure("res/log4j.properties");
		
		int choice = -1;

		int loginAttempts=0;
		while(choice!=3 && loginAttempts<=3){
			System.out.println("*******Welcom to Online Banking System**********");
			System.out.println("Login As--->");
			System.out.print("[1]Admin [2]Customer [3]Quit >");
			choice = scan.nextInt();
			
			if(choice==1){
				System.out.print("UserName? ");
				String adminUserName = scan.next();
				System.out.print("Password? ");
				String adminPassword = scan.next();
								
				try{
					boolean success=loginService.getAdminLogin(adminUserName, adminPassword);
					
					if(success){
						System.out.println("successfully logged in");
					}
				
				}catch(InvalidCredentialsException e){
					System.err.println(e.getMessage());
				}
				
			}else if(choice==2){
				System.out.println("Username? ");
				String customerUserName = scan.next();
				System.out.println("Password? ");
				String customerPassword = scan.next();
				loginAttempts++;
				
				try{
					boolean success=loginService.getUserLogin(customerUserName, customerPassword);
					
					if(success){
						UserClient userClient=new UserClient();
						//userClient.startClient();
					}
				
				}catch(InvalidCredentialsException e){
					System.err.println(e.getMessage());
				}
	
			}
			
			
		}
		scan.close();
		System.out.println("Program Terminated");
	}

	

}
