package com.cg.obs.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.service.AdminServiceImpl;
import com.cg.obs.service.IAdminService;

public class AdminConsole {

	static AdminConsole admin = new AdminConsole();
	Scanner sc = new Scanner(System.in);
	IAdminService adminService = new AdminServiceImpl();
	
	 private Logger log = Logger.getLogger("Admin");
	
	
	public static void main(String[] args) {
		
		 
		admin.adminConsole();
		

	}

	/*******************************************************************************************************
	 * - Function Name : isValid - Input Parameters : CabRequest Object - Return
	 * Type : boolean - Throws : CabException - Author : Aniruddhsinh Sodha -
	 * Creation Date : 29/08/2018 - Description : validation for entered data by
	 * user
	 ********************************************************************************************************/

	public void adminConsole() {

		PropertyConfigurator.configure("res/log4j.properties");
		log.info("Admin Console started..."); 
		 
		String choice;

		boolean check = true;

		System.out.println("***********************************");
		System.out.println("Admin Console");

		while (check) {

			System.out.println("\n***********************************");
			System.out.println("1. Create user Account");
			System.out.println("2. Get Transaction Details");
			System.out.println("3. Account lock status");
			System.out.println("4. Exit");
			System.out.println("Enter your choice");
			System.out.println("***********************************");
			choice = sc.nextLine();

			switch (choice) {
			case "1":
				log.info("Creating user Account");
				admin.createAccount();

				break;
			case "2":

				log.info("Getting transaction details for Admin");
				admin.getTransactionDetails();

				break;
			case "3":

				log.info("Getting account status");
				admin.accountLockStatus();

				break;
			case "4":
				
				log.info("Exiting Admin console");
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

		String customerName;
		String customerAddress;
		long customerMobileNum;
		String customerEmail;
		String panDetail;
		String accountType;
		double openingBalance;
		String existing;
		long userId = 0;
		Date currentDate = new Date();
		java.sql.Date openDate = new java.sql.Date(currentDate.getTime());
		String check;

		try {

			System.out.println("Existing Customer(y/n)");
			existing = sc.nextLine();

			if (existing.toLowerCase().equals("y")) {

				System.out.println("Enter User id : ");
				check = sc.nextLine();
				userId = Long.parseLong(check);
				System.out.println("Account Type : ");
				accountType = sc.nextLine();
				System.out.println("Opening Balance : ");
				check = sc.nextLine();
				openingBalance = Double.parseDouble(check);

				AccountMaster account = new AccountMaster();

				account.setUserId(userId);
				account.setAccountType(accountType);
				account.setOpeningBalance(openingBalance);
				account.setOpenDate(openDate);

				
				adminService.isValidateExistingUser(account);

				log.info("Adding Account details in database");
				boolean status = adminService.addAccountMaster(account);

				if (status == true) {
					log.info("Account Created");
					System.out.println("Account Created");
				}

			} else if (existing.toLowerCase().equals("n")) {

				System.out.println("Customer Name :");
				customerName = sc.nextLine();
				System.out.println("Customer Address : ");
				customerAddress = sc.nextLine();
				System.out.println("Customer Mobile Number : ");
				check = sc.nextLine();
				customerMobileNum = Long.parseLong(check);
				System.out.println("Customer Email Id : ");
				customerEmail = sc.nextLine();
				System.out.println("Account Type : ");
				accountType = sc.nextLine();
				System.out.println("PAN Card Number : ");
				panDetail = sc.nextLine();
				System.out.println("Opening Balance : ");
				check = sc.nextLine();
				openingBalance = Double.parseDouble(check);

				AccountMaster account = new AccountMaster();

				account.setAccountType(accountType);
				account.setOpeningBalance(openingBalance);
				account.setOpenDate(openDate);

				Customer cust = new Customer();

				cust.setCustomerName(customerName);
				cust.setAddress(customerAddress);
				cust.setMobile(customerMobileNum);
				cust.setEmail(customerEmail);
				cust.setPancard(panDetail);

				boolean statusAdd = false;
				boolean status = false;

				adminService.isValidate(cust, account);

				log.info("Adding new user details to database");
				
				userId = adminService.createNewUser();
				cust.setUserId(userId);
				account.setUserId(userId);

				log.info("Adding account details");
				statusAdd = adminService.addAccountMaster(account);
				status = adminService.addAccountDetails(cust);

				if (status == true && statusAdd == true) {
					
					log.info("Account created");
					System.out.println("Account Created");
				}

			} else {
				System.out.println("Enter valid Option");
			}

		} catch (NumberFormatException e) {

			log.error("Invalid entered data");
			System.err.println("Enter valid Input");

		} catch (OnlineBankingException e) {

			log.error("Exception occured");
			System.out.println(e.getMessage());

		}

	}

	private void getTransactionDetails() {

		log.info("Getting user transaction details");
		
		String sDate;
		String eDate;

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		List<Transactions> list = new ArrayList<Transactions>();

		System.out.println("Enter the starting date (dd/MM/yyyy) : ");
		sDate = sc.nextLine();
		System.out.println("Enter the end date (dd/MM/yyyy) : ");
		eDate = sc.nextLine();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {

			Date stDate = format.parse(sDate);
			startDate = new java.sql.Date(stDate.getTime());

			Date edDate = format.parse(eDate);
			endDate = new java.sql.Date(edDate.getTime());

			if (startDate.before(endDate)) {

				list = adminService.getTransactionDetails(startDate, endDate);

				if (list.size() == 0) {

					log.info("No transaction found");
					
					System.out.println("No Transaction exist for given dates");

				} else {

					System.out.println("Transaction Details \n");
					for (Transactions tra : list) {

						System.out.println(tra.toString());

					}
				}
			} else {
				
				log.error("Start date greater then end date");
				System.err.println("Start date can not be greater then end date");
			}

		} catch (ParseException e) {

			log.error("Invalid input date");
			System.err.println("Enter valid date format");

		} catch (OnlineBankingException e) {

			log.error("Exception occured");
			System.err.println(e.getMessage());

		}

	}

	private void accountLockStatus() {

		log.info("Getting User lock status");
		
		System.out.println("Enter User Id : ");
		String accId = sc.nextLine();
		int userID = 0;

		try {

			userID = Integer.parseInt(accId);

			String status = adminService.getLockStatus(userID);

			Customer customer = adminService.getCustomerDetails(userID);

			if (customer == null) {
				System.out
						.println("Sorry! User does not exist for given Account Number");

			} else {
				boolean check = true;
				if (status.equals("l")) {
					status = "Lock";
				} else {
					status = "UnLock";
				}

				System.out.println("Account Details \n");
				System.out.println("Account Number : " + userID);
				System.out.println("Customer Name : "
						+ customer.getCustomerName());
				System.out.println("Customer Mobile Number : "
						+ customer.getMobile());
				System.out.println("Customer Email Address : "
						+ customer.getEmail());
				System.out.println("Customer Address : "
						+ customer.getAddress());
				System.out.println("Locked Status : " + status);

				while (check) {

					System.out.println("\n1. Lock User Account");
					System.out.println("2. Unlock User Account");
					System.out.println("\nEnter your Choice");
					String choice = sc.nextLine();

					switch (choice) {
					case "1":
						
						
						if (status.equals("Lock")) {
							System.out.println("Account is already Locked");
						} else {
							status = "l";
							boolean change = adminService.changeAccountStatus(
									userID, status);
							if (change) {
								log.info("User accouint is locked");
								System.out.println("Account is Locked");
							} else {
								System.out
										.println("Problem occured while locking account");
							}

						}

						check = false;
						break;
					case "2":
						if (status.equals("UnLock")) {
							System.out.println("Account is already UnLocked");
						} else {
							status = "u";
							boolean change = adminService.changeAccountStatus(
									userID, status);
							if (change) {
								log.info("User account is unlicked");
								System.out.println("Account is UnLocked");
							} else {
								System.out
										.println("Problem occured while unlocking account");
							}
						}

						check = false;
						break;

					default:

						System.out.println("Enter a valid Choice");

						break;
					}
				}
			}

		} catch (NumberFormatException e) {

			log.error("Invalid account number");
			System.out.println("Enter Valid Account Number");

		} catch (OnlineBankingException e) {

			log.error("Exception occured");
			System.err.println(e.getMessage());

		}

	}

}
