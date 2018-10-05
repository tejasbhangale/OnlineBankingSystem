package com.cg.obs.dao;

import java.util.List;


import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;

import java.util.ArrayList;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer);

	public boolean checkOldPass(String oldPass, int id);

	public void updatePassword(String newPass, int id) throws PasswordUpdateException;

	public int requestChequeBook(int id);

	public List<Integer> getAccountList(int id);
	
	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError;

	public ServiceTracker getRequestStatus(int reqNum,int accNum);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);

	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError;

	public double getAccBalance(int accountId);

	public List<Integer> getPayeeList(int id);

	public boolean debitFunds(int accountID, double transferAmount);

	public boolean creditFunds(int accountID, double transferAmount);

	public int recordFundTransfer(int fromaccount, int toaccount,
			double transferAmount);

	public int recordTransaction(int accountId, int fundTransferId,
			String type, double transferAmount);

}
