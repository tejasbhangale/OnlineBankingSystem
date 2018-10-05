package com.cg.obs.service;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.dao.ILoginDao;
import com.cg.obs.exception.InvalidCredentialsException;
import com.cg.obs.util.Messages;
import com.cg.obs.util.OBSDaoFactory;

public class LoginServiceImpl implements ILoginService {

	private static ILoginDao loginDao=OBSDaoFactory.getLoginDao();
	@Override
	public boolean getAdminLogin(String username, String password) throws InvalidCredentialsException {
		// TODO Auto-generated method stub
		boolean success=false;
		
		
		Admin admin=loginDao.getAdminLogin(username);
		
		if(admin==null){
			throw new InvalidCredentialsException(Messages.INVALID_USERNAME);
		}else if(!password.equals(admin.getPassword())){
			throw new InvalidCredentialsException(Messages.INVALID_PASSWORD);
		}else{
			success=true;
		}
		
		
		return success;
	}

	@Override
	public int getUserLogin(int username, String password) throws InvalidCredentialsException {
		// TODO Auto-generated method stub
		boolean success=false;
		
		User user=loginDao.getUserLogin(username);
		
		if(user==null) {
			throw new InvalidCredentialsException(Messages.INVALID_USERNAME);
		}else if(!password.equals(user.getLoginPassword())) {
			throw new InvalidCredentialsException(Messages.INVALID_PASSWORD);
		}else if(user.getLockStatus().equals("l")) {
			throw new InvalidCredentialsException(Messages.ACCOUNT_LOCKED);
		}
		else {
			return user.getAccountId();
		}
		
		
		
	}

	@Override
	public boolean lockUserAccount(int id) {
		boolean success =loginDao.lockUserAccount(id);
		return success;
	}

	@Override
	public boolean validateUserId(int id) throws InvalidCredentialsException {
		int userId = loginDao.getUserId(id);
		boolean success=false; 
		if(userId!=0){
			success=true;
			
		}
		else {
			success= false;
			throw new InvalidCredentialsException(Messages.INVALID_USERNAME);
		}
		return success;
	}

	@Override
	public boolean validatePassword(int customerId, String customerPassword) throws InvalidCredentialsException {
		String verifyPass =loginDao.getPass(customerId);
		boolean success=false;
		if(!verifyPass.equals(customerPassword)) {
			success=false;
			throw new InvalidCredentialsException(Messages.INVALID_PASSWORD);
		}
		else{
			success=true;
		}
		return success;
	}

	@Override
	public User forgotPassword(int id) {
		// TODO Auto-generated method stub
		return loginDao.forgotPassword(id);
	}

	@Override
	public boolean setOneTimePassword(String newPassword,int id) {
		// TODO Auto-generated method stub
		return loginDao.setOneTimePassword(newPassword,id);
	}	

}
