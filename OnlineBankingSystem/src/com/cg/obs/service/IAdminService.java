package com.cg.obs.service;

import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.ValidationException;

public interface IAdminService {

	boolean addAccountDetails(Customer cust) throws JDBCConnectionError;

	boolean addAccountMaster(AccountMaster account) throws JDBCConnectionError;

	List<Transactions> getTransactionDetails(java.sql.Date startDate, java.sql.Date endDate) throws JDBCConnectionError;

	void isValidate(Customer customer, AccountMaster account) throws ValidationException;



}
