package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;

public class LoginDaoImpl implements ILoginDao {

	
	public LoginDaoImpl() {
		super();
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Admin getAdminLogin(String userId) {
		try {
			Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			PreparedStatement st =con.prepareStatement(IQueryMapper.ADMIN_CREDENTIALS);
			st.setString(1, userId);
			
		} catch (OnlineBankingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	@Override
	public User getUserLogin(String userId) {
		try {
			Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			PreparedStatement st =con.prepareStatement(IQueryMapper.ADMIN_CREDENTIALS);
			st.setString(1, userId);
			
		} catch (OnlineBankingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
