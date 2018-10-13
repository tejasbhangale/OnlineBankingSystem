package com.cg.obs.service;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.dao.ILoginDao;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSDaoFactory;

public class LoginServiceImpl implements ILoginService {

	private static ILoginDao loginDao=OBSDaoFactory.getLoginDao();
	
	
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getAdminLogin
	 - Input Parameters	:	String username, String password
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's getAdminLogin(userId) method
	 ********************************************************************************************************/
	@Override
	public boolean getAdminLogin(String username, String password) throws OnlineBankingException {
		boolean success=false;
		
		Admin admin=loginDao.getAdminLogin(username);
		
		if(admin==null){
			throw new OnlineBankingException(Messages.INVALID_USERNAME);
		}else if(!password.equals(admin.getAdminPassword())){
			throw new OnlineBankingException(Messages.INVALID_PASSWORD);
		}else{
			success=true;
		}
		
		
		return success;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	getUserLogin
	 - Input Parameters	:	long userID, String password
	 - Return Type		:	long 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's getUserLogin(userId) method
	 ********************************************************************************************************/

	@Override
	public long getUserLogin(long userID, String password) throws OnlineBankingException {
		long userId=0;
		
		User user=loginDao.getUserLogin(userID);
		
		if(user==null) {
			throw new OnlineBankingException(Messages.INVALID_USERNAME);
		}else if(!password.equals(user.getLoginPassword())) {
			throw new OnlineBankingException(Messages.INVALID_PASSWORD);
		}else if(user.getLockStatus().equals("l")) {
			throw new OnlineBankingException(Messages.ACCOUNT_LOCKED);
		}
		else {
			userId= user.getUserId();
			return userId;
		}
		
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	lockUserAccount
	 - Input Parameters	:	long userId
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's lockUserAccount(userId) method
	 ********************************************************************************************************/
	@Override
	public boolean lockUserAccount(long userId) throws OnlineBankingException {
		boolean success =loginDao.lockUserAccount(userId);
		return success;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	validateUserId
	 - Input Parameters	:	long userId
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's getUserId(userId) method
	 ********************************************************************************************************/
	@Override
	public boolean validateUserId(long userid) throws OnlineBankingException {
		int userId = loginDao.getUserId(userid);
		boolean success=false; 
		if(userId!=0){
			success=true;
			
		}
		else {
			success= false;
			throw new OnlineBankingException(Messages.INVALID_USERNAME);
		}
		return success;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	validatePassword
	 - Input Parameters	:	long customerId, String customerPassword
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's getPass(customerId) method
	 ********************************************************************************************************/
	@Override
	public boolean validatePassword(long customerId, String customerPassword) throws OnlineBankingException {
		String verifyPass =loginDao.getPass(customerId);
		boolean success=false;
		if(!verifyPass.equals(customerPassword)) {
			success=false;
			throw new OnlineBankingException(Messages.INVALID_PASSWORD);
		}
		else{
			success=true;
		}
		return success;
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	forgotPassword
	 - Input Parameters	:	long userId
	 - Return Type		:	User object
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's forgotPassword(id) method
	 ********************************************************************************************************/
	@Override
	public User forgotPassword(long userId) throws OnlineBankingException {
		return loginDao.forgotPassword(userId);
	}
	//------------------------ 1. Online Banking Application --------------------------
	/*******************************************************************************************************
	 - Function Name	:	setOneTimePassword
	 - Input Parameters	:	String newPassword,long userId
	 - Return Type		:	boolean 
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	calls dao's setOneTimePassword(ewPassword,id) method
	 ********************************************************************************************************/
	@Override
	public boolean setOneTimePassword(String newPassword,long userId) throws OnlineBankingException {
		// TODO Auto-generated method stub
		return loginDao.setOneTimePassword(newPassword,userId);
	}	

}
