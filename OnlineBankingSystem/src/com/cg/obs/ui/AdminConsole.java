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
import com.cg.obs.bean.User;
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

		while (check) {

			System.out.println("1. Create user Account");
			System.out.println("2. Get Transaction Details");
			System.out.println("3. Unlock User Account");
			System.out.println("4. Exit");
			System.out.println("Enter your choice");
			choice = sc.next();

			switch (choice) {
			case "1":

				admin.createAccount();
				// admin.test();

				break;
			case "2":

				admin.getTransactionDetails();

				break;
			case "3":

				admin.unlockUserAccount();

				break;
			case "4":
				System.out.println("Thank you for using our service");
                check = false;
				break;
			default:

				System.out.println("Please Enter valid option");

				break;
			}
		}

	}

	

	private void createAccount() {

		int accNumber;
		String customerName;
		String customerAddress;
		long customerMobileNum;
		String customerEmail;
		String panDetail;
		String accountType;
		double openingBalance;
		Date currentDate = new Date();
		java.sql.Date openDate = new java.sql.Date(currentDate.getTime());

		try {

			String check;

			System.out.println("Account Number : ");
			check = sc.next();
			accNumber = Integer.parseInt(check);
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
			openingBalance = Double.parseDouble(check);

			Customer cust = new Customer(accNumber, customerName,
					customerMobileNum, customerEmail, customerAddress,
					panDetail);

			AccountMaster account = new AccountMaster(accNumber, accountType,
					openingBalance, openDate);

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
	
	
private void unlockUserAccount() {
		
		  
	     System.out.println("Enter Account Number : ");
	     String accId = sc.next();
	     int accNumber = 0;
	     
	     try {
	    	 
	    	  accNumber = Integer.parseInt(accId);
	    	  
	    	  User user = adminService.getSecretQuestionAnswer(accNumber);
	    	  
	    	  if(user==null)
	    	  {
	    		  System.out.println("User does not exist");
	    	  }
	    	  else
	    	  {
	    		  if(user.getLockStatus()=='u')
	    		  {
	    			  System.out.println("Account id : "+user.getAccountId());
	    			  System.out.println("User id : "+user.getUserId());
	    			  System.out.println("Given account is not locked");
	    		  }
	    		  else
	    		  {
	    		  System.out.println("Answer the Question : "+user.getSecretQuestion());
	    		  String answer = sc.next();
	    		  
	    		  if(answer.toLowerCase().equals(user.getSecretAnswer().toLowerCase()))
	    		  {
	    			  boolean change = adminService.changeAccountStatus(accNumber);
	    			  
	    			  if(change)
	    			  {
	    				  System.out.println("Account id : "+user.getAccountId());
		    			  System.out.println("User id : "+user.getUserId());
	    				  System.out.println("Account has been unlocked");
	    			  }
	    			  
	    		  }
	    		  else
	    		  {
	    			  System.out.println("Given answer is not correct");
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
