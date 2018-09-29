package com.cg.obs.dao;

import com.cg.obs.bean.Customer;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public void updateCustomerDetails(Customer customer);

}