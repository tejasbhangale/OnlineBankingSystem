package com.cg.obs.service;

public interface ICustomerService {

	public boolean getAdminLogin(String username,String password);
	
	public boolean getUserLogin(String username,String password);
	
}
