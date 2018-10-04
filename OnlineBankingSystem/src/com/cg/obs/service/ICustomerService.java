package com.cg.obs.service;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;

public interface ICustomerService {
	
	public void validate(long mobile, String address) throws InvalidDetailsEntered;

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException;

	public boolean checkOldPass(String oldPass, int ar);

	public boolean checkNewPass(String newPass);

	public void updatePassword(String newPass, int id) throws PasswordUpdateException;

	public int requestChequeBook(int id);


}
