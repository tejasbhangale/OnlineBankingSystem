package com.cg.obs.dao;

import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.JDBCConnectionError;

public interface IAdminDAO {

	boolean addAccountDetails(Customer cust) throws JDBCConnectionError;

	boolean addAccountMaster(AccountMaster account) throws JDBCConnectionError;

	List<Transactions> getTransactionDetails(Date startDate, Date endDate) throws JDBCConnectionError;

}
