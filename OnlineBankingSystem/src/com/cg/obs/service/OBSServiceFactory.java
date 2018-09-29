package com.cg.obs.service;

public class OBSServiceFactory {
	
	public static CustomerServiceImpl getCustomerBean() {
		return new CustomerServiceImpl();
	}

}
