package com.cg.obs.util;

import com.cg.obs.dao.CustomerDaoImpl;
import com.cg.obs.dao.ICustomerDao;

public class OBSDaoFactory {

	public static ICustomerDao getCustomerDao() {
		
		return new CustomerDaoImpl();
	}



}
