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
	}

	@Override
	public Admin getAdminLogin(String userId) {
		
		Admin admin=null;
	
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st=con.prepareStatement(IQueryMapper.ADMIN_CREDENTIALS);) {
			
			st.setString(1, userId);
			resultset=st.executeQuery();
			
			if(resultset.next()){
				admin=new Admin();
				admin.setAdminId(resultset.getInt(1));
				admin.setUserId(resultset.getString(2));
				admin.setPassword(resultset.getString(3));
				
			}
			
			
			
		} catch (JDBCConnectionError e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		
		
		return admin;
	}

	@Override
	public User getUserLogin(int userId) {
		User user=null;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			PreparedStatement st =con.prepareStatement(IQueryMapper.USER_CREDENTIALS);) {
			
			st.setInt(1, userId);
			resultset=st.executeQuery();
			
			if(resultset.next()){
				user=new User();
				user.setUserId(resultset.getInt(1));
				user.setLoginPassword(resultset.getString(2));
				user.setAccountId(resultset.getInt(3));
				user.setLockStatus(resultset.getString(4));
			}
		} catch (JDBCConnectionError e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return user;
	}

	@Override
	public boolean lockUserAccount(int id) {
		boolean lockSuccess=false;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.LOCK_USER);) {
				
				st.setInt(1, id);
				int rows=st.executeUpdate();
				if(rows==0){
					lockSuccess= false;
				}
				else{
					lockSuccess=true;
				}
						
	} catch (SQLException e) {
		System.err.println(e.getMessage());
	} catch (JDBCConnectionError e1) {
		System.err.println(e1.getMessage());
	}
	
	return lockSuccess;

		
	}

	@Override
	public int getUserId(int userId) {
		int id=0;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_USER_ID);) {
				
				st.setInt(1, userId);
				resultset=st.executeQuery();
				if(resultset.next()){
					
					id=resultset.getInt(1);
				}
				
		} catch (JDBCConnectionError e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return id;
	}

	@Override
	public String getPass(int userId) {
		String passcode=null;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_USER_PASS)) {
				
				st.setInt(1, userId);
				resultset=st.executeQuery();
				if(resultset.next()){
					
					passcode=resultset.getString(1);
				}
				
		} catch (JDBCConnectionError e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return passcode;
	}

	@SuppressWarnings("null")
	@Override
	public User forgotPassword(int id) {
		// TODO Auto-generated method stub
		User user=null;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_FORGOT_PASSWORD_OBJECT)){
			
			st.setInt(1, id);
			resultset=st.executeQuery();
			if(resultset.next()){
				user=new User();
				user.setAccountId(resultset.getInt(1));
				user.setUserId(resultset.getInt(2));
				user.setLoginPassword(resultset.getString(3));
				user.setSecretAnswer(resultset.getString(4));
				user.setSecretQuestion(resultset.getString(5));
				user.setTransactionPassword(resultset.getString(6));
				user.setLockStatus(resultset.getString(7));
			
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}catch(JDBCConnectionError e){
			System.err.println(e.getMessage());
		}
		return user;
	}

	@Override
	public boolean setOneTimePassword(String newPassword,int id) {
		// TODO Auto-generated method stub
		boolean success=false;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.SET_ONE_TIME_PASSWORD)){
			st.setString(1, newPassword);
			st.setInt(2, id);
			int result=st.executeUpdate();
			if(result>0){
				success=true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}catch(JDBCConnectionError e){
			System.err.println(e.getMessage());
		}
		
		
		return success;
	}
}
	

