package com.cg.obs.util;

import com.cg.obs.service.CustomerServiceImpl;

public class OBSServiceFactory {
	
	public static CustomerServiceImpl getCustomerBean() {
		return new CustomerServiceImpl();
	}

}
