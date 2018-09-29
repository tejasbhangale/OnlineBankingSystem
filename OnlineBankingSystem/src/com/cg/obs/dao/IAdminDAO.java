package com.cg.obs.dao;

import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;

public interface IAdminDAO {

	boolean addAccountDetails(Customer cust);

	boolean addAccountMaster(AccountMaster account);

	List<Transactions> getTransactionDetails(Date startDate, Date endDate);

}
