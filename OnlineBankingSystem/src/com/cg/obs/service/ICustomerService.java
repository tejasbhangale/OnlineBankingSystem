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

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException;

	public void updatePassword(String newPass, int id) throws OnlineBankingException;

	public int requestChequeBook(int id);

	public List<Integer> getAccountList(long userId);

	public List<Transactions> getMiniStatement(int ar) throws OnlineBankingException;

	public ServiceTracker getRequestStatus(int reqNum,int userId);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);

	public List<Transactions> getDetailedStatement(int ar,
			java.sql.Date startDate, java.sql.Date endDate) throws OnlineBankingException;

	public boolean checkfunds(long fromaccount, double transferAmount);

	public List<Payee> getPayeeList(long id);

	public int transferfunds(long fromaccount, long toaccount, double transferAmount);

	public boolean addPayee(Payee payee) throws OnlineBankingException;

	public boolean transactionAuthentication(long userId, long verifyId,
			String verifyPass);
	public ArrayList<Integer> getAllAccounts(int userId);

	public boolean isFirstTimeUser(int userId);

	public String[] checkPass(String[] pass, int userId) throws OnlineBankingException;

	public boolean validateUserData(ArrayList<String> userData, int userId) throws OnlineBankingException;

	public void completeProfile(ArrayList<String> userData,int userId) throws OnlineBankingException;

}
