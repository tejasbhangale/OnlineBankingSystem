package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.InvalidPasswordEntered;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;

public interface ICustomerService {

	void validate(long mobile, String address) throws InvalidDetailsEntered;

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException;

	boolean checkOldPass(String oldPass, int ar);

	boolean checkNewPass(String newPass);

	void updatePassword(String newPass, int id) throws PasswordUpdateException;

}
