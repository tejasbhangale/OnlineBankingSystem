package com.cg.obs.dao;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.PasswordUpdateException;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer);

	public boolean checkOldPass(String oldPass, int id);

	public void updatePassword(String newPass, int id) throws PasswordUpdateException;

}
