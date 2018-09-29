package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.InvalidPasswordEntered;
import com.cg.obs.exception.UpdateCustomerException;

public interface ICustomerService {

	void validate(long mobile, String address) throws InvalidDetailsEntered;

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException;

	void validatePassword(String oldPass, String newPass1, String newPass2) throws InvalidPasswordEntered;

}
