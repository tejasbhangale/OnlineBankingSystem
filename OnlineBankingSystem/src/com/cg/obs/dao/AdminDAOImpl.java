package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.util.DBUtil;

public class AdminDAOImpl implements IAdminDAO {

	@Override
	public boolean addAccountDetails(Customer cust) {
		
		Connection conn = DBUtil.getConnection();
		int status = 0;
		
		
		try {
			PreparedStatement pstm = conn.prepareStatement(IQueryMapper.INSERT_ACCOUNT_DETAILS);
			pstm.setLong(1, cust.getAccountId());
			pstm.setString(2, cust.getCustomerName());
            pstm.setLong(3, cust.getMobile());
            pstm.setString(4, cust.getEmail());
            pstm.setString(5, cust.getAddress());
            pstm.setString(6, cust.getPancard());
            
            status = pstm.executeUpdate();
            
            
            
		} catch (SQLException e) {


			e.printStackTrace();
		}
		
		if(status>0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean addAccountMaster(AccountMaster account) {
		
		Connection conn = DBUtil.getConnection();
		int status = 0;
		

		try {
			PreparedStatement pstm = conn.prepareStatement(IQueryMapper.INSERT_ACCOUNT_MASTER);
			pstm.setLong(1, account.getAccountId());
			pstm.setString(2, account.getAccountType());
            pstm.setDouble(3, account.getOpeningBalance());
            pstm.setDate(4, (Date) account.getOpenDate());
           
            
            status = pstm.executeUpdate();
            
            
            
		} catch (SQLException e) {


			e.printStackTrace();
		}
		
		if(status>0)
		{
			return true;
		}
		
		
		return false;
	}


	@Override
	public List<Transactions> getTransactionDetails(
			Date startDate, Date endDate) {
		
		List<Transactions> list = new ArrayList<Transactions>();

		Connection conn = DBUtil.getConnection();


		try {
			PreparedStatement pstm = conn.prepareStatement(IQueryMapper.GET_TRANSACTION_DETAILS);
			
			
			pstm.setDate(1, startDate);
			pstm.setDate(2, endDate);

            
            ResultSet result =  pstm.executeQuery();
            
       
            
            while(result.next())
            {
            	Transactions tran = new Transactions();
            
            	tran.setTransactionId(result.getLong(1));
            	tran.setTransactionDesc(result.getString(2));
            	tran.setDateOfTransaction(result.getDate(3));
            	tran.setTransactionType(result.getString(4));
            	tran.setTransactionAmount(result.getDouble(5));
            	tran.setAccountId(result.getLong(6));
            	list.add(tran);
            	
            	
            }
            
            
            
		} catch (SQLException e) {


			e.printStackTrace();
		}
		
	
		
		return list;
	}

}
