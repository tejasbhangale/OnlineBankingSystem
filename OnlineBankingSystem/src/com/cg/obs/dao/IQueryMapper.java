
package com.cg.obs.dao;

public interface IQueryMapper {

	public static final String ADMIN_CREDENTIALS = "SELECT admin_id,user_id,password FROM admin_table WHERE user_id=?";
	
	public static final String USER_CREDENTIALS= "SELECT user_id,login_password,account_id,lock_status FROM user_table WHERE user_id=?";

	public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO customer VALUES (?,?,?,?,?,?)";

	public static final String INSERT_ACCOUNT_MASTER = "INSERT INTO Account_Master VALUES (?,?,?,?)";

	public static final String GET_TRANSACTION_DETAILS = "select * from Transactions where DateofTransaction>=? AND DateofTransaction<=?";

	public static final String GET_CUSTOMER_DETAILS = "Select * from Customer where Account_ID=?";
	
	public static final String UPDATE_CUSTOMER_DETAILS = "UPDATE Customer set mobile=?,address=? where Account_ID=?";

	public static final String CHECK_OLD_PASSWORD = "SELECT login_password FROM user_table WHERE account_id=?";

	public static final String UPDATE_CUSTOMER_PASSWORD = "UPDATE USER_TABLE set login_password=? where account_id=?";

}

