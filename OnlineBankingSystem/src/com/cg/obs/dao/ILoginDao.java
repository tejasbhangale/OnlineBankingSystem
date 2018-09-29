package com.cg.obs.dao;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;

public interface ILoginDao {
	
	public Admin getAdminLogin(String userId);
	public User getUserLogin(String userId);
	

}
