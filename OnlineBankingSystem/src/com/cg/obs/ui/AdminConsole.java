package com.cg.obs.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.service.AdminServiceImpl;
import com.cg.obs.service.IAdminService;

public class AdminConsole {

	static AdminConsole admin = new AdminConsole();
	Scanner sc = new Scanner(System.in);
	IAdminService adminService = new AdminServiceImpl();

	public static void main(String[] args) {

		admin.adminConsole();

	}

	public void adminConsole() {
		
		String choice;

		while (true) {

			System.out.println("1. Create user Account");
			System.out.println("2. Get Transaction Details");
			System.out.println("3. Exit");
			System.out.println("Enter your choice");
			choice = sc.next();

			switch (choice) {
			case "1":

				admin.createAccount();

				break;
			case "2":

				admin.getTransactionDetails();

				break;
			case "0":

				System.exit(0);
				break;
			default:

				System.out.println("Please Enter valid option");

				break;
			}
		}

	}

	private void createAccount() {

		long accNumber;
		String customerName;
		String customerAddress;
		long customerMobileNum;
		String customerEmail;
		String panDetail;
		String accountType;
		double openingBalance;
		Date currentDate = new Date();
		java.sql.Date openDate = new java.sql.Date(currentDate.getTime());
		
		
		
		System.out.println("Account Number : ");
		accNumber = sc.nextLong();
		System.out.println("Customer Name :");
		customerName = sc.next();
		System.out.println("Customer Address : ");
		customerAddress = sc.next();
		System.out.println("Customer Mobile Number : ");
		customerMobileNum = sc.nextLong();
		System.out.println("Customer Email Id : ");
		customerEmail = sc.next();
		System.out.println("Account Type : ");
		accountType = sc.next();
		System.out.println("PAN Card Number : ");
		panDetail = sc.next();
		System.out.println("Opening Balance : ");
		openingBalance = sc.nextDouble();
		
		Customer cust = new Customer(accNumber,customerName, customerAddress,  customerMobileNum, customerEmail, panDetail );
		
		AccountMaster account = new AccountMaster(accNumber, accountType, openingBalance, openDate);
		
		boolean statusAdd  = adminService.addAccountMaster(account);
		
		boolean status = adminService.addAccountDetails(cust);
		
		if(status==true && statusAdd==true)
		{
			System.out.println("Data added");
		}
		
		
	}

	private void getTransactionDetails() {

		
		String sDate;
		String eDate;
		
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		
		List<Transactions> list = new ArrayList<Transactions>();
		
		
		System.out.println("Enter the starting date : ");
		sDate = sc.next();
		System.out.println("Enter the end date : ");
		eDate = sc.next();
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			
			
			 Date stDate = format.parse(sDate);
			 startDate = new java.sql.Date(stDate.getTime());
			 
			 Date edDate = format.parse(eDate);
			 endDate = new java.sql.Date(edDate.getTime());
			
			 
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		list = adminService.getTransactionDetails(startDate, endDate);
		
    for (Transactions tra : list) {
    	
    	System.out.println(tra.toString());
		
	}		
		
	
		
		
	}

}
