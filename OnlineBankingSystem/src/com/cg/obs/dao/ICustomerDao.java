package com.cg.obs.dao;

import java.util.List;


import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Transactions;

import java.util.ArrayList;

import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer);

	public boolean checkOldPass(String oldPass, int id);

	public void updatePassword(String newPass, int id) throws OnlineBankingException;

	public int requestChequeBook(int id);

	public List<Integer> getAccountList(long id);
	
	public List<Transactions> getMiniStatement(int ar) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,int userId);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);

	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws OnlineBankingException;


	public double getAccBalance(long accountId);

	public List<Payee> getPayeeList(long id);

	public boolean debitFunds(long accountID, double transferAmount);

	public boolean creditFunds(long toaccount, double transferAmount);

	public int recordFundTransfer(long fromaccount, long toaccount,
			double transferAmount);

	public int recordTransaction(long fromaccount, int fundTransferId,
			String type, double transferAmount);

	public void addPayee(Payee payee) throws OnlineBankingException;

	public String getUserTransPassword(long userId);

	public ArrayList<Integer> getAllAccounts(int userId);

	public boolean isFirstTimeUser(int userId);

	public void completeProfile(ArrayList<String> userData, int userId) throws OnlineBankingException;

}
