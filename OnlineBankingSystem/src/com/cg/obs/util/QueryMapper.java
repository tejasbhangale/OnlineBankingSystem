package com.cg.obs.util;

public class QueryMapper {

	public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO Customer_Table VALUES (?,?,?,?,?,?)";
	
	public static final String INSERT_ACCOUNT_MASTER = "INSERT INTO Account_Master VALUES (?,?,?,?)";
	
	public static final String GET_TRANSACTION_DETAILS = "select * from Transactions where DateofTransaction>=? AND DateofTransaction<=?";
	
}
