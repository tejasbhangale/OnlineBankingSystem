package com.cg.obs.service;


import java.util.ArrayList;


import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSDaoFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.OBSDaoFactory;

public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();

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
	public Customer getCustomerDetails(int id) throws OnlineBankingException {
		return cDao.getCustomerDetails(id);
	}

	@Override
	public List<Integer> getAccountList(long id) throws OnlineBankingException {
		// TODO Auto-generated method stub
		return cDao.getAccountList(id);
	}
	@Override
	public boolean updateCustomerDetails(Customer customer)
			throws OnlineBankingException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public String[] checkPass(String[] pass, int userId)
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

	@Override
	public void updatePassword(String newPass, int id)
			throws OnlineBankingException {
		cDao.updatePassword(newPass, id);
	}

	@Override
	public int requestChequeBook(int accNum) throws OnlineBankingException {
		return cDao.requestChequeBook(accNum);
	}
	
		
	
	@Override
	public List<Transactions> getMiniStatement(int ar) throws OnlineBankingException {
		return cDao.getMiniStatement(ar);
	}

	@Override
	public ServiceTracker getRequestStatus(int reqNum, int userId) throws OnlineBankingException {
		return cDao.getRequestStatus(reqNum, userId);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) throws OnlineBankingException {
		return cDao.getAllRequestStatus(accNum);

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws OnlineBankingException {
		return cDao.getDetailedStatement(ar, startDate, endDate);
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
	public List<Payee> getPayeeList(long id) throws OnlineBankingException {
		return cDao.getPayeeList(id);
	}

	@Override
	public int transferfunds(long fromaccount, long toaccount, double transferAmount) throws OnlineBankingException {
		boolean success=false;
		int fundTransferId= cDao.recordFundTransfer(fromaccount,toaccount,transferAmount);
		int transactionId =0;
		try{
			transactionId = cDao.recordTransaction(fromaccount,fundTransferId,"d",transferAmount);
			cDao.debitFunds(fromaccount,transferAmount);
			
			try{
				cDao.creditFunds(toaccount,transferAmount);
			}catch(OnlineBankingException e){
				cDao.creditFunds(fromaccount, transferAmount);
				success=false;
			}
			
		} catch(OnlineBankingException e){
			
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		}
		success=cDao.creditFunds(toaccount,transferAmount);
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
	public boolean transactionAuthentication(long userId,String verifyPass) throws OnlineBankingException {
		
		String userpassword=cDao.getUserTransPassword(userId);
		if(userpassword.equals(verifyPass) ){
			return true;
		}
		return false;
	}


	@Override
	public ArrayList<Integer> getAllAccounts(int userId) throws OnlineBankingException {
		return cDao.getAllAccounts(userId);
	}

	@Override
	public boolean isFirstTimeUser(int userId) throws OnlineBankingException {
		return cDao.isFirstTimeUser(userId);
	}

	@Override
	public boolean validateUserData(ArrayList<String> userData, int userId)
			throws OnlineBankingException {
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
			throw new OnlineBankingException(errors.toString());
		}

	}

	@Override
	public void completeProfile(ArrayList<String> userData,int userId) throws OnlineBankingException {
		cDao.completeProfile(userData,userId);
	}

}
