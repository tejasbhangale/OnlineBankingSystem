package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;


public class CustomerServiceImpl implements ICustomerService {
	
	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao(); 
	
	@Override
	public void validate(long mobile, String address) throws InvalidDetailsEntered {
		
	}

	@Override
	public Customer getCustomerDetails(int id){
		return cDao.getCustomerDetails(id);
	}

	@Override
	public void updateCustomerDetails(Customer customer)  throws UpdateCustomerException {
		cDao.updateCustomerDetails(customer);
	}

	@Override
	public boolean getAdminLogin(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getUserLogin(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
