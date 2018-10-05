package com.cg.obs.service;


import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;

public interface ICustomerService {
	
	public void validate(long mobile, String address) throws InvalidDetailsEntered;

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException;

	public boolean checkOldPass(String oldPass, int ar);

	public boolean checkNewPass(String newPass);

	public void updatePassword(String newPass, int id) throws PasswordUpdateException;

	public int requestChequeBook(int id);


	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError;

	public ServiceTracker getRequestStatus(int reqNum,int accNum);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);

	public List<Transactions> getDetailedStatement(int ar,
			java.sql.Date startDate, java.sql.Date endDate) throws JDBCConnectionError;



}
