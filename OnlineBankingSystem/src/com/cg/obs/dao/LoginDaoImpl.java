package com.cg.obs.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class LoginDaoImpl implements ILoginDao {

	private ResultSet resultset;
	
	
	public LoginDaoImpl() {
		super();
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getAdminLogin
	 - Input Parameters	:	String userId
	 - Return Type		:	Admin object
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Getting Admin Credentials
	 ********************************************************************************************************/	
	@Override
	public Admin getAdminLogin(String userId) throws OnlineBankingException {
		
		Admin admin=null;
	
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st=con.prepareStatement(IQueryMapper.ADMIN_CREDENTIALS);) {
			
			st.setString(1, userId);
			resultset=st.executeQuery();
			
			if(resultset.next()){
				admin=new Admin();
				admin.setAdminId(resultset.getInt(1));
				admin.setAdminUserId(resultset.getString(2));
				admin.setAdminPassword(resultset.getString(3));
			}
			
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
		
		
		
		return admin;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getUserLogin
	 - Input Parameters	:	long userId
	 - Return Type		:	User object
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Getting User Credentials
	 ********************************************************************************************************/
	@Override
	public User getUserLogin(long userId) throws OnlineBankingException {
		User user=null;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			PreparedStatement st =con.prepareStatement(IQueryMapper.USER_CREDENTIALS);) {
			
			st.setLong(1, userId);
			resultset=st.executeQuery();
			
			if(resultset.next()){
				user=new User();
				user.setUserId(resultset.getInt(1));
				user.setLoginPassword(resultset.getString(2));
				user.setLockStatus(resultset.getString(3));
			}
		}catch (SQLException e) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		} 
		return user;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	lockUserAccount
	 - Input Parameters	:	long id
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Updating lock status of user account
	 ********************************************************************************************************/
	@Override
	public boolean lockUserAccount(long id) throws OnlineBankingException {
		boolean lockSuccess=false;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.LOCK_USER);) {
				
				st.setLong(1, id);
				int rows=st.executeUpdate();
				if(rows==0){
					lockSuccess= false;
				}
				else{
					lockSuccess=true;
				}
						
	} catch (SQLException e) {
		throw new OnlineBankingException(Messages.CHANGE_LOCK_STATUS);
	} 
	
	return lockSuccess;

		
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getUserId
	 - Input Parameters	:	long userId
	 - Return Type		:	int
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Getting User ID
	 ********************************************************************************************************/
	@Override
	public int getUserId(long userId) throws OnlineBankingException {
		int id=0;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_USER_ID);) {
				
				st.setLong(1, userId);
				resultset=st.executeQuery();
				if(resultset.next()){
					
					id=resultset.getInt(1);
				}
				
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		} 
		return id;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getPass
	 - Input Parameters	:	long userId
	 - Return Type		:	String
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Getting User's login Password
	 ********************************************************************************************************/
	@Override
	public String getPass(long userId) throws OnlineBankingException {
		String passcode=null;
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_USER_PASS)) {
				
				st.setLong(1, userId);
				resultset=st.executeQuery();
				if(resultset.next()){
					
					passcode=resultset.getString(1);
				}
				
		}  catch (SQLException e) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		} 
		return passcode;
	}

	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	forgotPassword
	 - Input Parameters	:	long id
	 - Return Type		:	User object 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Getting User details
	 ********************************************************************************************************/	
	@Override
	public User forgotPassword(long id) throws OnlineBankingException {
		User user=null;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.GET_FORGOT_PASSWORD_OBJECT)){
			
			st.setLong(1, id);
			resultset=st.executeQuery();
			if(resultset.next()){
				user=new User();
				user.setUserId(resultset.getInt(1));
				user.setLoginPassword(resultset.getString(2));
				user.setSecretAnswer(resultset.getString(3));
				user.setSecretQuestion(resultset.getString(4));
				user.setTransactionPassword(resultset.getString(5));
				user.setLockStatus(resultset.getString(6));
			
			}
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.FORGOT_PASSWORD);
		}
		return user;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	setOneTimePassword
	 - Input Parameters	:	String newPassword,long id
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Setting one time password for user
	 ********************************************************************************************************/
	@Override
	public boolean setOneTimePassword(String newPassword,long id) throws OnlineBankingException {
		boolean success=false;
		
		try(Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st =con.prepareStatement(IQueryMapper.SET_ONE_TIME_PASSWORD)){
			st.setString(1, newPassword);
			st.setLong(2, id);
			int result=st.executeUpdate();
			if(result>0){
				success=true;
			}
			
			
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
		
		
		return success;
	}
}
	

