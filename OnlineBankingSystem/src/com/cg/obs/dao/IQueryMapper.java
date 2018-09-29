package com.cg.obs.dao;

public interface IQueryMapper {

	public static final String ADMIN_CREDENTIALS = "SELECT admin_id,user_id,password FROM admin_table WHERE user_id=?";

	public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO Customer_Table VALUES (?,?,?,?,?,?)";

	public static final String INSERT_ACCOUNT_MASTER = "INSERT INTO Account_Master VALUES (?,?,?,?)";

	public static final String GET_TRANSACTION_DETAILS = "select * from Transactions where DateofTransaction>=? AND DateofTransaction<=?";

	public static final String GET_CUSTOMER_DETAILS = "Select * from Customer where Account_ID=?";
	
	public static final String UPDATE_CUSTOMER_DETAILS = "UPDATE Customer set mobile=?,address=? where Account_ID=?";

}
