package com.cg.obs.service;

import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.bean.User;

public interface IAdminService {

	boolean addAccountDetails(Customer cust) throws OnlineBankingException;

	boolean addAccountMaster(AccountMaster account) throws OnlineBankingException;

	List<Transactions> getTransactionDetails(java.sql.Date startDate, java.sql.Date endDate) throws OnlineBankingException;

	void isValidate(Customer customer, AccountMaster account) throws OnlineBankingException;

	boolean changeAccountStatus(int accNumber, String status) throws OnlineBankingException;

	String getLockStatus(int accNumber) throws OnlineBankingException;

	Customer getCustomerDetails(int accNumber) throws OnlineBankingException;

	long createNewUser() throws OnlineBankingException;

	void isValidateExistingUser(AccountMaster account) throws OnlineBankingException;




}
