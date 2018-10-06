package com.cg.obs.dao;

import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;

public interface IAdminDAO {

	boolean addAccountDetails(Customer cust) throws OnlineBankingException;

	boolean addAccountMaster(AccountMaster account) throws OnlineBankingException;

	List<Transactions> getTransactionDetails(Date startDate, Date endDate) throws OnlineBankingException;


	boolean changeAccountStatus(int accNumber, String status) throws OnlineBankingException;

	String getLockStatus(int accNumber) throws OnlineBankingException;

	Customer getCustomerDetails(int accNumber) throws OnlineBankingException;

	long createNewUser() throws OnlineBankingException;

}
