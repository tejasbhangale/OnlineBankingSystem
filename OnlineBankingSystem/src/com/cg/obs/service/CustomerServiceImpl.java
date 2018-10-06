
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
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;



public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();

	@Override
	public void validate(long mobile, String address) throws InvalidDetailsEntered {
		ArrayList<String> errors = new ArrayList<String>();

		if (mobile < Long.valueOf("1000000000")) {
			errors.add("Mobile number must have 10 digits!");
		}
		if (mobile < Long.valueOf("7000000000")) {
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
	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public boolean checkOldPass(String oldPass, int id) {
		return cDao.checkOldPass(oldPass, id);
	}

	@Override
	public boolean checkNewPass(String newPass) {
		String[] pass = newPass.split(" ");
		if (!(pass[0].equals(pass[1]))) {
			System.err.println("Password do not match!");
			return false;
		} else if (pass[0].length() <= 5) {
			System.err.println("Password length must be greater than 5 characters!");
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String newPass, int id) throws PasswordUpdateException {
		cDao.updatePassword(newPass, id);
	}

	@Override
	public int requestChequeBook(int id) {
		// TODO Auto-generated method stub
		return cDao.requestChequeBook(id);
	}

	@Override
	public List<Integer> getAccountList(long id) {
		// TODO Auto-generated method stub
		return cDao.getAccountList(id);
	}
	@Override
	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError {
		return cDao.getMiniStatement(ar);
	}
	
    @Override
	public ServiceTracker getRequestStatus(int reqNum,int accNum) {
		// TODO Auto-generated method stub
		return cDao.getRequestStatus(reqNum,accNum);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		// TODO Auto-generated method stub
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

}

