package com.cg.obs.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.ValidationException;
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

		boolean check = true;

		System.out.println("\nAdmin Console\n");

		while (check) {

			System.out.println("1. Create user Account");
			System.out.println("2. Get Transaction Details");
			System.out.println("3. Account lock status");
			System.out.println("4. Exit");
			System.out.println("Enter your choice");
			choice = sc.next();

			switch (choice) {
			case "1":

				admin.createAccount();

				break;
			case "2":

				admin.getTransactionDetails();

				break;
			case "3":

				admin.accountLockStatus();

				break;
			case "4":
				System.out.println("Thank you for using our service");

				check=false;

				break;
			default:

				System.out.println("Please Enter valid option");

				break;
			}
		}

	}

	

	private void createAccount() {

		
		String customerName;
		String customerAddress;
		long customerMobileNum;
		String customerEmail;
		String panDetail;
		String accountType;
		double openingBalance;
		String existing;
		long userId;
		Date currentDate = new Date();
		java.sql.Date openDate = new java.sql.Date(currentDate.getTime());

		try {

			String check;
			
			System.out.println("Customer Name :");
			customerName = sc.next();
			System.out.println("Customer Address : ");
			customerAddress = sc.next();
			System.out.println("Customer Mobile Number : ");
			check = sc.next();
			customerMobileNum = Long.parseLong(check);
			System.out.println("Customer Email Id : ");
			customerEmail = sc.next();
			System.out.println("Account Type : ");
			accountType = sc.next();
			System.out.println("PAN Card Number : ");
			panDetail = sc.next();
			System.out.println("Opening Balance : ");
			check = sc.next();
			System.out.println("Existing Customer(y/n)");
			existing = sc.next();
			
			if(existing.toLowerCase().equals("y"))
			{
				System.out.println("Enter User id : ");
				check = sc.next();
				userId = Long.parseLong(check);
				
			}
			
			openingBalance = Double.parseDouble(check);

			Customer cust = new Customer();
			
			cust.setCustomerName(customerName);
			cust.setAddress(customerAddress);
			cust.setMobile(customerMobileNum);
			cust.setEmail(customerEmail);
			cust.setPancard(panDetail);
			
			AccountMaster account = new AccountMaster();
			
			account.setAccountType(accountType);
			account.setOpeningBalance(openingBalance);
			account.setOpenDate(openDate);

			boolean statusAdd = false;
			boolean status = false;

			try {

				adminService.isValidate(cust, account);
				statusAdd = adminService.addAccountMaster(account);
				status = adminService.addAccountDetails(cust);

				if (status == true && statusAdd == true) {
					System.out.println("Data added");
				}

			} catch (ValidationException e2) {

				System.err.println(e2.getMessage());
			} catch (JDBCConnectionError e1) {

				System.err.println(e1.getMessage());

			}

		} catch (NumberFormatException e) {

			System.err.println("Enter valid Input");

		}

	}

	private void getTransactionDetails() {

		String sDate;
		String eDate;

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		List<Transactions> list = new ArrayList<Transactions>();

		System.out.println("Enter the starting date (dd/MM/yyyy) : ");
		sDate = sc.next();
		System.out.println("Enter the end date (dd/MM/yyyy) : ");
		eDate = sc.next();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {

			Date stDate = format.parse(sDate);
			startDate = new java.sql.Date(stDate.getTime());

			Date edDate = format.parse(eDate);
			endDate = new java.sql.Date(edDate.getTime());

			if (startDate.before(endDate)) {
				list = adminService.getTransactionDetails(startDate, endDate);

				if (list.size() == 0) {

					System.out.println("No Transaction exist for given dates");

				} else {

					for (Transactions tra : list) {

						System.out.println(tra.toString());

					}
				}
			} else {
				System.err
						.println("Start date can not be greater than end date");
			}

		} catch (ParseException e) {

			System.err.println("Enter valid date format");

		} catch (JDBCConnectionError e) {

			System.err.println(e.getMessage());

		}

	}
	
	
private void accountLockStatus() {
		
		  
	     System.out.println("Enter Account Number : ");
	     String accId = sc.next();
	     int accNumber = 0;
	     
	     try {
	    	 
	    	  accNumber = Integer.parseInt(accId);
	    	  
	    	  String status = adminService.getLockStatus(accNumber);
	    	  
	    	  Customer customer = adminService.getCustomerDetails(accNumber);
	    	  
	    	  if(customer==null)
	    	  {
	    		  System.out.println("Sorry! User does not exist for given Account Number");
	    		  
	    	  }
	    	  else
	    	  {
	    	  boolean check = true;
	    	  if(status.equals("l"))
	    	  {
	    		  status = "Lock";
	    	  }
	    	  else
	    	  {
	    		  status = "UnLock";
	    	  }
	    	  
	    	  
	    	  
	    	  System.out.println("Account Details");
	    	  System.out.println("Account Number : "+accNumber);
	    	  System.out.println("Customer Name : "+customer.getCustomerName());
	    	  System.out.println("Customer Mobile Number : "+customer.getMobile());
	    	  System.out.println("Customer Email Address : "+customer.getEmail());
	    	  System.out.println("Customer Address : "+customer.getAddress());
	    	  System.out.println("Locked Status : "+status);
	    	  
	    	  while(check)
	    	  {
	    		  
	    		  System.out.println("1. Lock User Account");
		    	  System.out.println("2. Unlock User Account");
		    	  System.out.println("Enter your Choice");
		    	  String choice = sc.next();
		    	  
	    	  switch (choice) {
			case "1":
				if(status.equals("Lock"))
				{
					System.out.println("Account is already Locked");
				}
				else
				{
					status = "l";
					boolean change = adminService.changeAccountStatus(accNumber, status);
					if(change)
					{
						System.out.println("Account is Locked");
					}
					else
					{
						System.out.println("Problem occured while locking account");
					}
					
				}
				
				check=false;
				break;
			case "2":
				if(status.equals("UnLock"))
				{
					System.out.println("Account is already UnLocked");
				}
				else
				{
					status = "u";
					boolean change = adminService.changeAccountStatus(accNumber, status);
					if(change)
					{
						System.out.println("Account is UnLocked");
					}
					else
					{
						System.out.println("Problem occured while unlocking account");
					}
				}
				
				check=false;
				break;

			default:
				
				System.out.println("Enter a valid Choice");
				
				break;
			}
	    	  }
	    	  }
			
		} catch (NumberFormatException e) {

		  System.out.println("Enter Valid Account Number");
		
		} catch (JDBCConnectionError e) {
			
			System.err.println(e.getMessage());
			
		}
	     
	
	    
	    	
		
			
		
	
	}

}



