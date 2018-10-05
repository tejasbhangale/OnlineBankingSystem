package com.cg.obs.dao;


import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;

import java.util.ArrayList;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;

import com.cg.obs.exception.PasswordUpdateException;

public interface ICustomerDao {

	public Customer getCustomerDetails(int id);

	public boolean updateCustomerDetails(Customer customer);

	public boolean checkOldPass(String oldPass, int id);

	public void updatePassword(String newPass, int id) throws PasswordUpdateException;

	public int requestChequeBook(int id);


	public List<Transactions> getMiniStatement(int ar);

	public ServiceTracker getRequestStatus(int reqNum,int accNum);

	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum);


}
