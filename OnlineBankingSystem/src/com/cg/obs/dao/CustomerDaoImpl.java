package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;

public class CustomerDaoImpl implements ICustomerDao {

	@Override
	public Customer getCustomerDetails(int id) {
		try {
			Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			String query = "Select * from Customer where Account_ID=?";
			PreparedStatement pt = conn.prepareStatement(query);
			pt.setInt(1, id);
			
		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public void updateCustomerDetails(Customer customer) {
		// TODO Auto-generated method stub
		
	}

}
