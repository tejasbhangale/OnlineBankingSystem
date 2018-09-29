package com.cg.obs.service;

import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.AdminDAOImpl;
import com.cg.obs.dao.IAdminDAO;

public class AdminServiceImpl implements IAdminService {

	IAdminDAO adminDAO = new AdminDAOImpl();
	
	
	@Override
	public boolean addAccountDetails(Customer cust) {
	
		return adminDAO.addAccountDetails(cust);
	}


	@Override
	public boolean addAccountMaster(AccountMaster account) {
		
		return adminDAO.addAccountMaster(account);
	}


	@Override
	public List<Transactions> getTransactionDetails(
			Date startDate, Date endDate) {
		
		return adminDAO.getTransactionDetails(
				startDate,endDate);
	}

}
