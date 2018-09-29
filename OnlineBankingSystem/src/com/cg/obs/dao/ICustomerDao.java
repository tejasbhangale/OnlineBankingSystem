package com.cg.obs.dao;

import com.cg.obs.bean.Customer;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer);

	public boolean checkOldPass(String oldPass, String string);

}
