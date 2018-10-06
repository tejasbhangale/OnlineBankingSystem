package com.cg.obs.dao;

import java.util.List;


import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;

import java.util.ArrayList;

import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.exception.CompleteProfileException;
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

	public ServiceTracker getRequestStatus(int reqNum,int userId);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);

	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError;

	public ArrayList<Integer> getAllAccounts(int userId);

	public boolean isFirstTimeUser(int userId);

	public void completeProfile(ArrayList<String> userData, int userId) throws CompleteProfileException;

}
