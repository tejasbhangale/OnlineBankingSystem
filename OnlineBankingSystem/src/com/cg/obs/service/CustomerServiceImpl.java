package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.InvalidPasswordEntered;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;

public class CustomerServiceImpl implements ICustomerService {
	
	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao(); 
	
	@Override
	public void validate(long mobile, String address) throws InvalidDetailsEntered {
		if(mobile<Long.valueOf("7000000000")){
			throw new InvalidDetailsEntered("mobile");
		}
		else if(address.equals("") | address.length()<3){
			throw new InvalidDetailsEntered("address");
		}
	}

	@Override
	public Customer getCustomerDetails(int id){
		return cDao.getCustomerDetails(id);
	}

	@Override
	public boolean updateCustomerDetails(Customer customer)  throws UpdateCustomerException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public void validatePassword(String oldPass, String newPass1,
			String newPass2) throws InvalidPasswordEntered {
		//cDao.checkPass(oldPass);
		
	}

}
