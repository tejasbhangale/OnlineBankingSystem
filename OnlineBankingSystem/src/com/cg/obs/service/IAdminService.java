package com.cg.obs.service;

import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;

public interface IAdminService {

	boolean addAccountDetails(Customer cust);

	boolean addAccountMaster(AccountMaster account);

	List<Transactions> getTransactionDetails(java.sql.Date startDate, java.sql.Date endDate);



}
