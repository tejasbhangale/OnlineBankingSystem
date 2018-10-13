package com.cg.obs.dao;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;

public interface ILoginDao {
	
	public Admin getAdminLogin(String userId) throws OnlineBankingException;
	
	public User getUserLogin(long username) throws OnlineBankingException;
	
	public boolean lockUserAccount(long id) throws OnlineBankingException;
	
	public int getUserId(long id) throws OnlineBankingException;
	
	public String getPass(long customerId) throws OnlineBankingException;
	
	public User forgotPassword(long id) throws OnlineBankingException;
	
	public boolean setOneTimePassword(String newPassword, long id) throws OnlineBankingException;
	
}
