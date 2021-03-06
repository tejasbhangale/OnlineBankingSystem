package com.cg.obs.service;


import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;


public interface ICustomerService {
	
	public void validate(long mobile, String address) throws OnlineBankingException;

	public Customer getCustomerDetails(long userId) throws OnlineBankingException;

	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException;

	public void updatePassword(String newPass, long userId) throws OnlineBankingException;

	public int requestChequeBook(long userId) throws OnlineBankingException;

	public List<Integer> getAccountList(long userId) throws OnlineBankingException;

	public List<Transactions> getMiniStatement(long accNum) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,long userId) throws OnlineBankingException;

	public ArrayList<ServiceTracker> getAllRequestStatus(long accNum) throws OnlineBankingException;

	public List<Transactions> getDetailedStatement(long accId,
			java.sql.Date startDate, java.sql.Date endDate) throws OnlineBankingException;

	public boolean checkfunds(long fromaccount, double transferAmount) throws OnlineBankingException;

	public List<Payee> getPayeeList(long userId) throws OnlineBankingException;

	public int transferfunds(long fromaccount, long toaccount, double transferAmount) throws OnlineBankingException;

	public boolean addPayee(Payee payee, long userId) throws OnlineBankingException;

	public boolean transactionAuthentication(long userId, String verifyPass) throws OnlineBankingException;
	
	public ArrayList<Long> getAllAccounts(long userId) throws OnlineBankingException;

	public boolean isFirstTimeUser(long userId) throws OnlineBankingException;

	public String[] checkPass(String[] pass, long userId) throws OnlineBankingException;

	public boolean validateUserData(ArrayList<String> userData, long userId) throws OnlineBankingException;

	public void completeProfile(ArrayList<String> userData,long userId) throws OnlineBankingException;

}
