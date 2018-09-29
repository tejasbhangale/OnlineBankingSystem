package com.cg.obs.service;

import com.cg.obs.exception.InvalidCredentialsException;

public interface ILoginService {
	
	
	public boolean getAdminLogin(String username,String password) throws InvalidCredentialsException;
	
	public boolean getUserLogin(String username,String password) throws InvalidCredentialsException;
	


}
