package com.cg.obs.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.ICustomerDao;
import com.cg.obs.exception.CompleteProfileException;
import com.cg.obs.exception.IncorrectPasswordException;
import com.cg.obs.exception.InvalidDetailsEntered;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.exception.UpdateCustomerException;
import com.cg.obs.util.OBSDaoFactory;

public class CustomerServiceImpl implements ICustomerService {

	private static ICustomerDao cDao = OBSDaoFactory.getCustomerDao();

	@Override
	public void validate(long mobile, String address)
			throws InvalidDetailsEntered {
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
	public boolean updateCustomerDetails(Customer customer)
			throws UpdateCustomerException {
		return cDao.updateCustomerDetails(customer);
	}

	@Override
	public String[] checkPass(String[] pass, int userId)
			throws IncorrectPasswordException {
		ArrayList<String> errors = new ArrayList<String>();
		if (!cDao.checkOldPass(pass[0], userId)) {
			errors.add("Incorrect Old Password");
		}
		if (!(pass[1].equals(pass[2]))) {
			errors.add("New Passwords do not match!");
		}
		if (pass[1].length() <= 5 | pass[2].length() <= 5) {
			errors.add("Password length must be greater than 5 characters!");
		}
		if (errors.isEmpty()) {
			return pass;
		} else {
			throw new IncorrectPasswordException(errors.toString());
		}
	}

	@Override
	public void updatePassword(String newPass, int id)
			throws PasswordUpdateException {
		cDao.updatePassword(newPass, id);
	}

	@Override
	public int requestChequeBook(int accNum) {
		return cDao.requestChequeBook(accNum);
	}
	
	@Override
	public List<Integer> getAccountList(int id) {
		return cDao.getAccountList(id);
	}
	
	@Override
	public List<Transactions> getMiniStatement(int ar) throws JDBCConnectionError {
		return cDao.getMiniStatement(ar);
	}

	@Override
	public ServiceTracker getRequestStatus(int reqNum, int userId) {
		return cDao.getRequestStatus(reqNum, userId);
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		return cDao.getAllRequestStatus(accNum);

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError {
		return cDao.getDetailedStatement(ar, startDate, endDate);
	}

	@Override
	public ArrayList<Integer> getAllAccounts(int userId) {
		return cDao.getAllAccounts(userId);
	}

	@Override
	public boolean isFirstTimeUser(int userId) {
		return cDao.isFirstTimeUser(userId);
	}

	@Override
	public boolean validateUserData(ArrayList<String> userData, int userId)
			throws InvalidDetailsEntered {
		ArrayList<String> errors = new ArrayList<String>();

		if (!cDao.checkOldPass((userData.get(0)), userId)) {
			errors.add("Invalid old password!");
		}
		if (userData.get(1).length() <= 5) {
			errors.add("Password length must be greater than 5 characters!");
		}
		if (userData.get(2).length() <= 5) {
			errors.add("Question must be atleast 5 characters long!");
		}
		if (userData.get(3).isEmpty()) {
			errors.add("Answer cannot be empty");
		}

		if (errors.isEmpty()) {
			return true;
		} else {
			throw new InvalidDetailsEntered(errors.toString());
		}

	}

	@Override
	public void completeProfile(ArrayList<String> userData,int userId) throws CompleteProfileException {
		cDao.completeProfile(userData,userId);
	}

}
