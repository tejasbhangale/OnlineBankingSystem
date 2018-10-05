
package com.cg.obs.service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;



public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();

	@Override
	public void validate(long mobile, String address) throws InvalidDetailsEntered {
		ArrayList<String> errors = new ArrayList<String>();

		if (mobile < Long.valueOf("1000000000")) {
			errors.add("Mobile number must have 10 digits!");
		}
		if (mobile < Long.valueOf("7000000000")) {
			errors.add("Mobile number must start with 7,8 or 9.");
		}
		if (address.equals("")) {
			errors.add("Address cannot be empty");
		}
		if (address.length() < 3) {
			errors.add("Address length must be greater than 3");
		}

		if (!errors.isEmpty())
			throw new InvalidDetailsEntered(errors.toString());

	}

	@Override
	public Customer getCustomerDetails(int id) {
		return cDao.getCustomerDetails(id);
	}

	@Override
	public boolean updateCustomerDetails(Customer customer) throws UpdateCustomerException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public boolean checkOldPass(String oldPass, int id) {
		return cDao.checkOldPass(oldPass, id);
	}

	@Override
	public boolean checkNewPass(String newPass) {
		String[] pass = newPass.split(" ");
		if (!(pass[0].equals(pass[1]))) {
			System.err.println("Password do not match!");
			return false;
		} else if (pass[0].length() <= 5) {
			System.err.println("Password length must be greater than 5 characters!");
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String newPass, int id) throws PasswordUpdateException {
		cDao.updatePassword(newPass, id);
	}

	@Override
	public int requestChequeBook(int id) {
		// TODO Auto-generated method stub
		return cDao.requestChequeBook(id);
	}

	@Override

	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError {
		
		return cDao.getMiniStatement(ar);
	}
	
    @Override
	public ServiceTracker getRequestStatus(int reqNum,int accNum) {
		// TODO Auto-generated method stub
		return cDao.getRequestStatus(reqNum,accNum);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		// TODO Auto-generated method stub
		return cDao.getAllRequestStatus(accNum);

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError {
		
		return cDao.getDetailedStatement(ar, startDate, endDate);
	}

}

