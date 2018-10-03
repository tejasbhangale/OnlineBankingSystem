package com.cg.obs.util;

import com.cg.obs.dao.CustomerDaoImpl;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.dao.ILoginDao;
import com.cg.obs.dao.LoginDaoImpl;

public class OBSDaoFactory {

	public static ICustomerDao getCustomerDao() {
		
		return new CustomerDaoImpl();
	}
	
	public static ILoginDao getLoginDao(){
		return new LoginDaoImpl();
	}



}
