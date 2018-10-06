package com.cg.obs.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.bean.User;
import com.cg.obs.dao.AdminDAOImpl;
import com.cg.obs.dao.IAdminDAO;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;

public class AdminServiceImpl implements IAdminService {

	IAdminDAO adminDAO = new AdminDAOImpl();
	
	
	@Override
	public boolean addAccountDetails(Customer cust) throws OnlineBankingException {
	
		return adminDAO.addAccountDetails(cust);
	}


	@Override
	public boolean addAccountMaster(AccountMaster account) throws OnlineBankingException {
		
		return adminDAO.addAccountMaster(account);
	}


	@Override
	public List<Transactions> getTransactionDetails(
			Date startDate, Date endDate) throws OnlineBankingException {
		
		return adminDAO.getTransactionDetails(
				startDate,endDate);
	}
	
	

	
	@Override
	public boolean changeAccountStatus(int accNumber, String status) throws OnlineBankingException {
		
		return adminDAO.changeAccountStatus(accNumber, status);
	}

	
	@Override
	public String getLockStatus(int accNumber) throws OnlineBankingException {
		
		return adminDAO.getLockStatus(accNumber);
	}
	
	@Override
	public Customer getCustomerDetails(int accNumber) throws OnlineBankingException {

		return adminDAO.getCustomerDetails(accNumber);
	}


	@Override
	public long createNewUser() throws OnlineBankingException {
		
		return adminDAO.createNewUser();
	}
	
	@Override
	public void isValidateExistingUser(AccountMaster account) throws OnlineBankingException{
		
		List<String> validation = new ArrayList<String>();
		
		if(!isValidUserID(account.getUserId()))
		{
			validation.add("\nEnter 3 digit User ID");
		}
		
		
		if(!isValidAccType(account.getAccountType()))
		{
			validation.add("Account type can be Saving or Current");
		}
		
		

		if(!isValidOpeningBalance(account.getOpeningBalance()))
		{
			validation.add("Enter valid Opening Balance");
		}
		

		if(!validation.isEmpty())
		{
			throw new OnlineBankingException(validation+"");
		}
		
	}


	@Override
	public void isValidate(Customer customer, AccountMaster account) throws OnlineBankingException
	{
		List<String> validation = new ArrayList<String>();
		
		
		
		if(!isValidName(customer.getCustomerName()))
		{
			validation.add("\nEnter valid name");
		}
		
		if(!isValidMobileNumber(customer.getMobile()))
		{
			validation.add("\nEnter valid 10 digit Mobile Number starting with 7,8 or 9");
		}
		
		if(!isValidEmail(customer.getEmail()))
		{
			validation.add("Enter valid Email Address");
		}
		
		if(!isValidPanCard(customer.getPancard()))
		{
			validation.add("Enter valid Pan Card Number");
		}
		
		if(!isValidAddress(customer.getAddress()))
		{
			validation.add("Enter valid Address");
		}
		

		if(!isValidOpeningBalance(account.getOpeningBalance()))
		{
			validation.add("Enter valid Opening Balance");
		}
		
		if(!isValidAccType(account.getAccountType()))
		{
			validation.add("Account type can be Saving or Current");
		}
		
		
		if(!validation.isEmpty())
		{
			throw new OnlineBankingException(validation+"");
		}
		
	}
	
	
	
	public boolean isValidUserID(long userId)
	{
		
		if(Long.toString(userId).length()==3)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isValidName(String custName)
	{
		
		Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+");
		Matcher match = pattern.matcher(custName);
		return match.matches();
		
	}
	
	public boolean isValidMobileNumber(long mobNumber)
	{
		
		Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}"); 
		Matcher match = pattern.matcher(Long.toString(mobNumber));
		
		
		
		
		return match.matches();
		
	}
	
	public boolean isValidEmail(String email)
	{
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		Matcher match = pattern.matcher(email);
		return match.matches();
		
		
	}
	
	public boolean isValidAddress(String address)
	{
		if(address.length()>=5)
		{
			return true;
		}
		
		return false;
		
		
	}
	
	
	public boolean isValidPanCard(String panCard)
	{
		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		Matcher match = pattern.matcher(panCard);
		return match.matches();
		
	}
	
	public boolean isValidAccType(String accType)
	{
		if(accType.toLowerCase().equals("savings") || accType.toLowerCase().equals("current"))
		{
			return true;
		}
		
		return false;
		
		
	}
	
	public boolean isValidOpeningBalance(double openingBalance)
	{
		if(openingBalance>=0)
		{
			return true;
		}
		
		return false;
		
		
		
	}


	





	


	


	

	
	

}
