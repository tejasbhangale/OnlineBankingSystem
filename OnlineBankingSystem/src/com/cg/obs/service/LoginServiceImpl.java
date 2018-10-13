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
		 - Return Type		:	boolean success
		 - Throws			:  	OnlineBankingException
		 - Author			:	CAPGEMINI
		 - Creation Date	:	11/11/2016
		 - Description		:	adding donor to database calls dao method addDonorDetails(donor)
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

	@Override
	public int getUserLogin(long username, String password) throws OnlineBankingException {
		int user_id=0;
		
		User user=loginDao.getUserLogin(username);
		
		if(user==null) {
			throw new OnlineBankingException(Messages.INVALID_USERNAME);
		}else if(!password.equals(user.getLoginPassword())) {
			throw new OnlineBankingException(Messages.INVALID_PASSWORD);
		}else if(user.getLockStatus().equals("l")) {
			throw new OnlineBankingException(Messages.ACCOUNT_LOCKED);
		}
		else {
			user_id=(int) user.getUserId();
			return user_id;
		}
		
	}

	@Override
	public boolean lockUserAccount(long id) throws OnlineBankingException {
		boolean success =loginDao.lockUserAccount(id);
		return success;
	}

	@Override
	public boolean validateUserId(long id) throws OnlineBankingException {
		int userId = loginDao.getUserId(id);
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

	@Override
	public User forgotPassword(long id) throws OnlineBankingException {
		// TODO Auto-generated method stub
		return loginDao.forgotPassword(id);
	}

	@Override
	public boolean setOneTimePassword(String newPassword,long id) throws OnlineBankingException {
		// TODO Auto-generated method stub
		return loginDao.setOneTimePassword(newPassword,id);
	}	

}
