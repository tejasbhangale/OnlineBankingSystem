package com.cg.obs.dao;

public class OBSDaoFactory {
	
	public static LoginDaoImpl getLoginDaoBean(){
		return new LoginDaoImpl();
	}

}
