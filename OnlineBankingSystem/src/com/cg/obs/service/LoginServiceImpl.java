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
	public boolean getUserLogin(String username, String password) throws InvalidCredentialsException {
		// TODO Auto-generated method stub
		boolean success=false;
		
		User user=loginDao.getUserLogin(username);
		
		if(user==null){
			throw new InvalidCredentialsException(Messages.INVALID_USERNAME);
		}else if(!password.equals(user.getLoginPassword())){
			throw new InvalidCredentialsException(Messages.INVALID_PASSWORD);
		}else{
			success=true;
		}
		
		
		return success;
	}

}
