package com.cg.obs.service;


import java.util.ArrayList;


import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.CompleteProfileException;
import com.cg.obs.exception.IncorrectPasswordException;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;

public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();

	@Override
	public void validate(long mobile, String address)
			throws InvalidDetailsEntered {
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
			throw new InvalidDetailsEntered(errors.toString());

	}

	@Override
	public Customer getCustomerDetails(int id) {
		return cDao.getCustomerDetails(id);
	}

	@Override
	public List<Integer> getAccountList(long id) {
		// TODO Auto-generated method stub
		return cDao.getAccountList(id);
	}
	@Override
	public boolean updateCustomerDetails(Customer customer)
			throws UpdateCustomerException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public String[] checkPass(String[] pass, int userId)
			throws IncorrectPasswordException {
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
			throw new IncorrectPasswordException(errors.toString());
		}
	}

	@Override
	public void updatePassword(String newPass, int id)
			throws PasswordUpdateException {
		cDao.updatePassword(newPass, id);
	}

	@Override
	public int requestChequeBook(int accNum) {
		return cDao.requestChequeBook(accNum);
	}
	
		
	
	@Override
	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError {
		return cDao.getMiniStatement(ar);
	}

	@Override
	public ServiceTracker getRequestStatus(int reqNum, int userId) {
		return cDao.getRequestStatus(reqNum, userId);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		return cDao.getAllRequestStatus(accNum);

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError {
		return cDao.getDetailedStatement(ar, startDate, endDate);
	}

	@Override
	public boolean checkfunds(long accountId, double transfer_amt) {
		double balance=cDao.getAccBalance(accountId);
		if(balance<transfer_amt){
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public List<Payee> getPayeeList(long id) {
		// TODO Auto-generated method stub
		return cDao.getPayeeList(id);
	}

	@Override
	public int transferfunds(long fromaccount, long toaccount, double transferAmount) {
		boolean transferSuccess=false;
		if(cDao.debitFunds(fromaccount,transferAmount)){
			transferSuccess=cDao.creditFunds(toaccount,transferAmount);
		}
		int fundTransferId= cDao.recordFundTransfer(fromaccount,toaccount,transferAmount);
		int transactionId = cDao.recordTransaction(fromaccount,fundTransferId,"d",transferAmount);
		cDao.recordTransaction(toaccount,fundTransferId,"c",transferAmount);
		return transactionId;
	}

	@Override
	public boolean addPayee(Payee payee) throws OnlineBankingException {
		boolean payeeFlag=false;
		System.out.println("in service");
		List<Payee> payeeList=getPayeeList(payee.getAccountId());
		List<Integer> accountList=getAccountList(payee.getAccountId());
		payee.setAccountId(accountList.get(0));
		int i,count =payeeList.size();
		System.out.println("count ="+count);
		if(count>0){
			for(i=0;i<count;i++){
				System.out.println(payeeList.get(i).getPayeeAccountId()+"     "+payee.getPayeeAccountId());
				if(payeeList.get(i).getPayeeAccountId()==payee.getPayeeAccountId()){
					
					payeeFlag=false;
					System.out.println(payeeFlag);
					break;
				}
		}
		}
			else{ 
				cDao.addPayee(payee);
				payeeFlag=true;
			}
		
		return payeeFlag;
	}

	@Override
	public boolean transactionAuthentication(long userId, long verifyId,
			String verifyPass) {
		
		String userpassword=cDao.getUserTransPassword(userId);
		if((userId==verifyId) && userpassword.equals(verifyPass) ){
			return true;
		}
		return false;
	}


	@Override
	public ArrayList<Integer> getAllAccounts(int userId) {
		return cDao.getAllAccounts(userId);
	}

	@Override
	public boolean isFirstTimeUser(int userId) {
		return cDao.isFirstTimeUser(userId);
	}

	@Override
	public boolean validateUserData(ArrayList<String> userData, int userId)
			throws InvalidDetailsEntered {
		ArrayList<String> errors = new ArrayList<String>();

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
			throw new InvalidDetailsEntered(errors.toString());
		}

	}

	@Override
	public void completeProfile(ArrayList<String> userData,int userId) throws CompleteProfileException {
		cDao.completeProfile(userData,userId);
	}

}
