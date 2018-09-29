package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.UpdateCustomerException;

public class CustomerServiceImpl implements ICustomerService {

	@Override
	public void validate(long mobile, String address) throws InvalidDetailsEntered {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomerDetails(int id){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomerDetails(Customer customer)  throws UpdateCustomerException {
		// TODO Auto-generated method stub
		
	}

}
