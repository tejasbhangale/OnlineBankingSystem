package com.cg.obs.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.util.ConnectionProvider;

public class LoginDaoImpl implements ILoginDao {

	private ResultSet resultset;
	
	
	public LoginDaoImpl() {
		super();
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Admin getAdminLogin(String userId) {
	
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st=con.prepareStatement(IQueryMapper.ADMIN_CREDENTIALS);) {
			
			st.setString(1, userId);
			resultset=st.executeQuery();
			
			Admin admin=new Admin();
			
			if(resultset.next()){
			
			
				admin.setAdminId(resultset.getInt(1));
				admin.setUserId(resultset.getString(2));
				admin.setPassword(resultset.getString(3));
				
			}
			
			return admin;
			
		} catch (JDBCConnectionError e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		
		
		return null;
	}

	@Override
	public User getUserLogin(String userId) {
		User user=new User();
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			PreparedStatement st =con.prepareStatement(IQueryMapper.USER_CREDENTIALS);) {
			
			st.setInt(1, Integer.parseInt(userId));
			resultset=st.executeQuery();
			
			
			
			if(resultset.next()){
				user.setUserId(resultset.getInt(1));
				user.setLoginPassword(resultset.getString(2));
				user.setAccountId(resultset.getInt(3));
				user.setLockStatus(resultset.getString(4));
				
				
			}
			
			
		} catch (JDBCConnectionError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	

}

