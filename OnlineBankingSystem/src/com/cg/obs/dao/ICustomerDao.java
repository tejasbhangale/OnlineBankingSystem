package com.cg.obs.dao;

import java.util.List;
import java.sql.Date;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Transactions;

import java.util.ArrayList;

import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.exception.OnlineBankingException;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id) throws OnlineBankingException;

	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException;

	public boolean checkOldPass(String oldPass, int id) throws OnlineBankingException;

	public void updatePassword(String newPass, int id) throws OnlineBankingException;

	public int requestChequeBook(int id) throws OnlineBankingException;

	public List<Integer> getAccountList(long id) throws OnlineBankingException;
	
	public List<Transactions> getMiniStatement(long accNum) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,int userId) throws OnlineBankingException;

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) throws OnlineBankingException;

	public List<Transactions> getDetailedStatement(long accNum, Date startDate,
			Date endDate) throws OnlineBankingException;

	public double getAccBalance(long accountId) throws OnlineBankingException;

	public List<Payee> getPayeeList(long id) throws OnlineBankingException;

	public boolean debitFunds(long accountID, double transferAmount) throws OnlineBankingException;

	public boolean creditFunds(long toaccount, double transferAmount) throws OnlineBankingException;

	public int recordFundTransfer(long fromaccount, long toaccount,
			double transferAmount) throws OnlineBankingException;

	public int recordTransaction(long fromaccount, int fundTransferId,
			String type, double transferAmount) throws OnlineBankingException;

	public void addPayee(Payee payee) throws OnlineBankingException;

	public String getUserTransPassword(long userId) throws OnlineBankingException;

	public ArrayList<Integer> getAllAccounts(int userId) throws OnlineBankingException;

	public boolean isFirstTimeUser(int userId) throws OnlineBankingException;

	public void completeProfile(ArrayList<String> userData, int userId) throws OnlineBankingException;

}
