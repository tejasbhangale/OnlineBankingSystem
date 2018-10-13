package com.cg.obs.service;

import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;

public interface ILoginService {
	
	
	public boolean getAdminLogin(String username,String password) throws OnlineBankingException;
	
	public int getUserLogin(long customerId,String password) throws OnlineBankingException;

	public boolean lockUserAccount(long customerId) throws OnlineBankingException;

	public boolean validateUserId(long customerId) throws OnlineBankingException;

	public boolean validatePassword(long customerId, String customerPassword) throws OnlineBankingException;
	
	public User forgotPassword(long id) throws OnlineBankingException;

	public boolean setOneTimePassword(String newPassword, long id) throws OnlineBankingException;

	
	


}
