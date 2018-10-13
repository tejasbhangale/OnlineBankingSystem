package com.cg.obs.service;

import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;

public interface IAdminService {

	boolean addAccountDetails(Customer cust) throws OnlineBankingException;

	boolean addAccountMaster(AccountMaster account) throws OnlineBankingException;

	List<Transactions> getTransactionDetails(java.sql.Date startDate, java.sql.Date endDate) throws OnlineBankingException;

	void isValidate(Customer customer, AccountMaster account) throws OnlineBankingException;

	boolean changeAccountStatus(Long accNumber, String status) throws OnlineBankingException;

	String getLockStatus(Long accNumber) throws OnlineBankingException;

	Customer getCustomerDetails(Long accNumber) throws OnlineBankingException;

	long createNewUser() throws OnlineBankingException;

	void isValidateExistingUser(AccountMaster account) throws OnlineBankingException;




}
