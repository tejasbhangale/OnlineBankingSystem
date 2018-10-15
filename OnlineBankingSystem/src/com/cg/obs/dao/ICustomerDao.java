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

	public Customer getCustomerDetails(long id) throws OnlineBankingException;

	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException;

	public boolean checkOldPass(String oldPass, long userId) throws OnlineBankingException;

	public void updatePassword(String newPass, long id) throws OnlineBankingException;

	public int requestChequeBook(long id) throws OnlineBankingException;

	public List<Integer> getAccountList(long id) throws OnlineBankingException;
	
	public List<Transactions> getMiniStatement(long accNum) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,long userId) throws OnlineBankingException;

	public ArrayList<ServiceTracker> getAllRequestStatus(long accNum) throws OnlineBankingException;

	public List<Transactions> getDetailedStatement(long accNum, Date startDate,
			Date endDate) throws OnlineBankingException;

	public double getAccBalance(long accountId) throws OnlineBankingException;

	public List<Payee> getPayeeList(long id) throws OnlineBankingException;

	public int recordFundTransfer(long fromaccount, long toaccount,
			double transferAmount) throws OnlineBankingException;

	public void addPayee(Payee payee) throws OnlineBankingException;

	public String getUserTransPassword(long userId) throws OnlineBankingException;

	public ArrayList<Long> getAllAccounts(long userId) throws OnlineBankingException;

	public boolean isFirstTimeUser(long userId) throws OnlineBankingException;

	public void completeProfile(ArrayList<String> userData, long userId) throws OnlineBankingException;

	public int transferfunds(long fromaccount, long toaccount,
			double transferAmount) throws OnlineBankingException;

	public boolean isValidAccount(long accountId) throws OnlineBankingException;

}
