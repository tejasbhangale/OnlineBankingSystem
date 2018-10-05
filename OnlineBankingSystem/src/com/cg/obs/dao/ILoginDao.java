package com.cg.obs.dao;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;

public interface ILoginDao {
	
	public Admin getAdminLogin(String userId);
	public User getUserLogin(int username);
	public boolean lockUserAccount(int id);
	public int getUserId(int userId);
	public String getPass(int customerId);
	public User forgotPassword(int id);
	public boolean setOneTimePassword(String newPassword, int id);
	

}
