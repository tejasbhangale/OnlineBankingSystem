package com.cg.obs.service;


import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;

public interface ICustomerService {
	
	public void validate(long mobile, String address) throws OnlineBankingException;

	public Customer getCustomerDetails(int id) throws OnlineBankingException;

	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException;

	public void updatePassword(String newPass, int id) throws OnlineBankingException;

	public int requestChequeBook(int id) throws OnlineBankingException;

	public List<Integer> getAccountList(long userId) throws OnlineBankingException;

	public List<Transactions> getMiniStatement(long accNum) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,int userId) throws OnlineBankingException;

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) throws OnlineBankingException;

	public List<Transactions> getDetailedStatement(long accId,
			java.sql.Date startDate, java.sql.Date endDate) throws OnlineBankingException;

	public boolean checkfunds(long fromaccount, double transferAmount) throws OnlineBankingException;

	public List<Payee> getPayeeList(long id) throws OnlineBankingException;

	public int transferfunds(long fromaccount, long toaccount, double transferAmount) throws OnlineBankingException;

	public boolean addPayee(Payee payee) throws OnlineBankingException;

	public boolean transactionAuthentication(long userId, String verifyPass) throws OnlineBankingException;
	
	public ArrayList<Integer> getAllAccounts(int userId) throws OnlineBankingException;

	public boolean isFirstTimeUser(int userId) throws OnlineBankingException;

	public String[] checkPass(String[] pass, int userId) throws OnlineBankingException;

	public boolean validateUserData(ArrayList<String> userData, int userId) throws OnlineBankingException;

	public void completeProfile(ArrayList<String> userData,int userId) throws OnlineBankingException;

}
