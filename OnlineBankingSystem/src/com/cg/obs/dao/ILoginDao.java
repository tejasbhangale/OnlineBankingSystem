package com.cg.obs.dao;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.exception.OnlineBankingException;

public interface ILoginDao {
	
	public Admin getAdminLogin(String userId) throws OnlineBankingException;
	
	public User getUserLogin(int username) throws OnlineBankingException;
	
	public boolean lockUserAccount(int id) throws OnlineBankingException;
	
	public int getUserId(int userId) throws OnlineBankingException;
	
	public String getPass(int customerId) throws OnlineBankingException;
	
	public User forgotPassword(int id) throws OnlineBankingException;
	
	public boolean setOneTimePassword(String newPassword, int id) throws OnlineBankingException;
	
}
