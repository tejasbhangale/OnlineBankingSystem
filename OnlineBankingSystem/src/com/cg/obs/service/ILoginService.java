package com.cg.obs.service;

import com.cg.obs.exception.InvalidCredentialsException;

public interface ILoginService {
	
	
	public boolean getAdminLogin(String username,String password) throws InvalidCredentialsException;
	
	public int getUserLogin(int customerUserName,String password) throws InvalidCredentialsException;

	public boolean lockUserAccount(int id);

	public boolean validateUserId(int id) throws InvalidCredentialsException;

	public boolean validatePassword(int customerId, String customerPassword) throws InvalidCredentialsException;

	
	


}
