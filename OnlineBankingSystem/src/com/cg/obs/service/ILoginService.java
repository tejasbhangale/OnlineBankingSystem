package com.cg.obs.service;

import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;

public interface ILoginService {
	
	
	public boolean getAdminLogin(String username,String password) throws OnlineBankingException;
	
	public int getUserLogin(int customerUserName,String password) throws OnlineBankingException;

	public boolean lockUserAccount(int id);

	public boolean validateUserId(int id) throws OnlineBankingException;

	public boolean validatePassword(int customerId, String customerPassword) throws OnlineBankingException;
	
	public User forgotPassword(int id);

	public boolean setOneTimePassword(String newPassword, int id);

	
	


}
