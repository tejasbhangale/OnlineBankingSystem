package com.cg.obs.ui;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;


public class ClientMain {
	
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
				String userName = scan.next();
				System.out.print("Password? ");
				String password = scan.next();
				loginAttempts++;				
			}
		}
		scan.close();
		System.out.println("Program Terminated");
	}

	

}
