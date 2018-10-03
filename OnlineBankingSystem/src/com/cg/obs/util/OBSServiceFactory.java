package com.cg.obs.util;


import com.cg.obs.service.CustomerServiceImpl;
import com.cg.obs.service.ILoginService;
import com.cg.obs.service.LoginServiceImpl;

public class OBSServiceFactory {
	
	public static CustomerServiceImpl getCustomerBean() {
		return new CustomerServiceImpl();
	}

	public static ILoginService getLoginService(){
		return new LoginServiceImpl();
	}
}
