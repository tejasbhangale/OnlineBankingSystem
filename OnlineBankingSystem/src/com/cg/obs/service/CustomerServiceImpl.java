package com.cg.obs.service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSDaoFactory;


public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();


	/*******************************************************************************************************
	 - Function Name	: validate(long mobile, String address)
	 - Input Parameters	: long mobile, String address
	 - Return Type		: void
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: validates the mobile number and address
	 ********************************************************************************************************/
	
	@Override
	public void validate(long mobile, String address)
			throws OnlineBankingException {
		ArrayList<String> errors = new ArrayList<String>();

		if (mobile < Long.valueOf("1000000000")) {
			errors.add("Mobile number must have 10 digits!");
		}
		if (!String.valueOf(mobile).matches("[789]\\d+")) {
			errors.add("Mobile number must start with 7,8 or 9.");
		}
		if (address.equals("")) {
			errors.add("Address cannot be empty");
		}
		if (address.length() < 3) {
			errors.add("Address length must be greater than 3");
		}

		if (!errors.isEmpty())
			throw new OnlineBankingException(errors.toString());

	}

	@Override
	public Customer getCustomerDetails(long userId) throws OnlineBankingException {
		return cDao.getCustomerDetails(userId);
	}

	@Override
	public List<Integer> getAccountList(long userId) throws OnlineBankingException {
		
		return cDao.getAccountList(userId);
	}
	@Override
	public boolean updateCustomerDetails(Customer customer)
			throws OnlineBankingException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public String[] checkPass(String[] pass, long userId)
			throws OnlineBankingException {
		ArrayList<String> errors = new ArrayList<String>();
		if (!cDao.checkOldPass(pass[0], userId)) {
			errors.add("Incorrect Old Password");
		}
		if (!(pass[1].equals(pass[2]))) {
			errors.add("New Passwords do not match!");
		}
		if (pass[1].length() <= 5 | pass[2].length() <= 5) {
			errors.add("Password length must be greater than 5 characters!");
		}
		if (errors.isEmpty()) {
			return pass;
		} else {
			throw new OnlineBankingException(errors.toString());
		}
	}

	/*******************************************************************************************************
	 - Function Name	: updatePassword(String newPass, long userId)
	 - Input Parameters	: String newPass, long userId
	 - Return Type		: void
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to initiates the request for change password for requested userId 
	 ********************************************************************************************************/
	@Override
	public void updatePassword(String newPass, long userId)
			throws OnlineBankingException {
		cDao.updatePassword(newPass, userId);
	}

	/*******************************************************************************************************
	 - Function Name	: requestChequeBook(long accNum)
	 - Input Parameters	: long accNum
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to the request for checkbook for a particular bank account number
	 ********************************************************************************************************/
	@Override
	public int requestChequeBook(long accNum) throws OnlineBankingException {
		return cDao.requestChequeBook(accNum);
	}
	
		
	/*******************************************************************************************************
	 - Function Name	: getMiniStatement(long accNum) 
	 - Input Parameters	: long accNum
	 - Return Type		: List<Transactions>
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to produce mini statement for the requested account number containing first 10 transactions 
	 ********************************************************************************************************/
	@Override
	public List<Transactions> getMiniStatement(long accNum) throws OnlineBankingException {
		return cDao.getMiniStatement(accNum);
	}

	/*******************************************************************************************************
	 - Function Name	: getRequestStatus(int reqNum, long userId)
	 - Input Parameters	: int reqNum, long userId
	 - Return Type		: ServiceTracker Bean
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to initiates the request for tracking service request status
	 ********************************************************************************************************/
	@Override
	public ServiceTracker getRequestStatus(int reqNum, long userId) throws OnlineBankingException {
		return cDao.getRequestStatus(reqNum, userId);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(long accNum) throws OnlineBankingException {
		return cDao.getAllRequestStatus(accNum);

	}
	
	
	/*******************************************************************************************************
	 - Function Name	:  getDetailedStatement(long accNum, Date startDate, Date endDate)
	 - Input Parameters	: long accNum, Date startDate,Date endDate
	 - Return Type		: List<Transactions>
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to produce detailed statement for the requested account number
	 ********************************************************************************************************/

	@Override
	public List<Transactions> getDetailedStatement(long accNum, Date startDate,
			Date endDate) throws OnlineBankingException {
		return cDao.getDetailedStatement(accNum, startDate, endDate);
	}

	@Override
	public boolean checkfunds(long accountId, double transfer_amt) throws OnlineBankingException {
		double balance=cDao.getAccBalance(accountId);
		if(balance<transfer_amt){
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public List<Payee> getPayeeList(long userId) throws OnlineBankingException {
		return cDao.getPayeeList(userId);
	}
	
	

	/*******************************************************************************************************
	 - Function Name	: transferfunds(long fromaccount, long toaccount, double transferAmount)
	 - Input Parameters	: long fromaccount, long toaccount, double transferAmount
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to initiate fund transfer request and record transaction
	 ********************************************************************************************************/

	@Override
	public int transferfunds(long fromaccount, long toaccount, double transferAmount) throws OnlineBankingException {
		
		int transactionId= cDao.transferfunds(fromaccount,toaccount,transferAmount);
		return transactionId;
	}
	

	/*******************************************************************************************************
	 - Function Name	: addPayee(Payee payee)
	 - Input Parameters	: Payee Bean
	 - Return Type		: boolean
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to add beneficiary information for the requested user account
	 ********************************************************************************************************/

	@Override
	public boolean addPayee(Payee payee,long userId) throws OnlineBankingException {
		boolean payeeFlag=true;
		//boolean payeeExist=false;
		List<Payee> payeeList = getPayeeList(userId);
		int i,count =payeeList.size();
		if(isValidAccount(payee.getPayeeAccountId())){
			
			if(count>0){
				for(i=0;i<count;i++){
					if(payeeList.get(i).getPayeeAccountId()==payee.getPayeeAccountId()){
						payeeFlag=true;
						break;
					}
				}
			}
			if(payeeFlag){ 
				cDao.addPayee(payee);
				payeeFlag=true;
			}	
		}else{
			throw new OnlineBankingException(Messages.PAYEE_ACCOUNT_ID_INVALID);
		}

		return payeeFlag;
	}

	private boolean isValidAccount(long accountId) throws OnlineBankingException{
		return cDao.isValidAccount(accountId);
	}

	/*******************************************************************************************************
	 - Function Name	: transactionAuthentication(long userId,String verifyPass)
	 - Input Parameters	: long userId,String verifyPass
	 - Return Type		: boolean
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to authenticate the user credentials for performing transaction
	 ********************************************************************************************************/

	
	@Override
	public boolean transactionAuthentication(long userId,String verifyPass) throws OnlineBankingException {
		
		String userpassword=cDao.getUserTransPassword(userId);
		if(userpassword.equals(verifyPass) ){
			return true;
		}
		return false;
	}

	/*******************************************************************************************************
	 - Function Name	: getAllAccounts(long userId)
	 - Input Parameters	: long userId
	 - Return Type		: ArrayList<Integer>
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to fetch all the registered account of user
	 ********************************************************************************************************/

	@Override
	public ArrayList<Long> getAllAccounts(long userId) throws OnlineBankingException {
		return cDao.getAllAccounts(userId);
	}

	@Override
	public boolean isFirstTimeUser(long userId) throws OnlineBankingException {
		return cDao.isFirstTimeUser(userId);
	}

	
	
	/*******************************************************************************************************
	 - Function Name	: validateUserData(ArrayList<String> userData, long userId)
	 - Input Parameters	: ArrayList<String> userData, long userId
	 - Return Type		: void
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: validates the userData
	 ********************************************************************************************************/
	
	@Override
	public boolean validateUserData(ArrayList<String> userData, long userId)
			throws OnlineBankingException {
		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * For userData ArrayList
		 * 0: old user password
		 * 1: new transaction password
		 * 2: secret question
		 * 3: secret answer
		 */
		if (!cDao.checkOldPass((userData.get(0)), userId)) {
			errors.add("Invalid old password!");
		}
		if (userData.get(1).length() <= 5) {
			errors.add("Password length must be greater than 5 characters!");
		}
		if (userData.get(2).length() <= 5) {
			errors.add("Question must be atleast 5 characters long!");
		}
		if (userData.get(3).isEmpty()) {
			errors.add("Answer cannot be empty");
		}

		if (errors.isEmpty()) {
			return true;
		} else {
			throw new OnlineBankingException(errors.toString());
		}

	}
	
	
	/*******************************************************************************************************
	 - Function Name	: completeProfile(ArrayList<String> userData,long userId)
	 - Input Parameters	: ArrayList<String> userData, long userId
	 - Return Type		: void
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: calls dao method to complete user profile for first time user
	 ********************************************************************************************************/

	@Override
	public void completeProfile(ArrayList<String> userData,long userId) throws OnlineBankingException {
		cDao.completeProfile(userData,userId);
	}

}
