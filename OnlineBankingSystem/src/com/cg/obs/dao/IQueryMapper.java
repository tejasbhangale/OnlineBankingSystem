
package com.cg.obs.dao;

public interface IQueryMapper {

	public static final String ADMIN_CREDENTIALS = "SELECT admin_id,admin_user_id,admin_password FROM admin_table WHERE admin_user_id=?";
	
	public static final String USER_CREDENTIALS= "SELECT user_id,login_password,lock_status FROM user_table WHERE user_id=?";
	
	public static final String GET_ACCOUNT_NUMBER = "SELECT ACCNUM_SEQ.NEXTVAL from DUAL";

	public static final String GET_USER_ID_SEQ = "SELECT USERID_SEQ.NEXTVAL from DUAL";

	public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO Customer VALUES (?,?,?,?,?,?)";

	public static final String INSERT_ACCOUNT_MASTER = "INSERT INTO Account_Master VALUES (?,?,?,?,?)";
	
	public static final String INSERT_USER_TABLE = "INSERT INTO User_Table VALUES (?,?,?,?,?,?)";	

	public static final String GET_TRANSACTION_DETAILS = "SELECT * from Transactions where Date_of_Transaction>=? AND Date_of_Transaction<=?";

	public static final String GET_CUSTOMER_DETAILS = "SELECT * from Customer where USER_ID=?";
	
	public static final String UPDATE_CUSTOMER_DETAILS = "UPDATE CUSTOMER SET MOBILE=?,ADDRESS=? WHERE USER_ID=?";

	public static final String CHECK_OLD_PASSWORD = "SELECT LOGIN_PASSWORD FROM USER_TABLE WHERE USER_ID=?";

	public static final String UPDATE_CUSTOMER_PASSWORD = "UPDATE USER_TABLE SET LOGIN_PASSWORD=? WHERE USER_ID=?";
	
	public static final String GET_LOCK_STATUS = "SELECT lock_status from User_Table where User_ID=?";

	public static final String CHANGE_ACCOUNT_STATUS = "UPDATE USER_TABLE set lock_status=? where User_ID=?";

	public static final String LOCK_USER = "UPDATE user_table SET lock_status='l' WHERE user_id=?";

	public static final String GET_USER_ID = "SELECT user_id FROM user_table WHERE user_id=?";

	public static final String GET_USER_PASS = "SELECT login_password FROM user_table WHERE user_id=?";;;

	public static final String GENERATE_SERVICE_REQUEST = "INSERT into SERVICE_TRACKER VALUES(service.nextval,?,?,?,?)";

	public static final String GET_SELF_ACCOUNTS = "SELECT account_id FROM account_master WHERE user_id=?";

	public static final String GET_SERVICE_REQUEST_NUMBER = "SELECT service.currval from dual";

	public static final String GET_MINI_STATEMENT = "SELECT * from Transactions where Account_Id=?";

	public static final String GET_REQUEST_STATUS = "SELECT * FROM SERVICE_TRACKER WHERE SERVICE_ID=? AND ACCOUNT_ID=? AND SERVICE_RAISED_DATE>(SYSDATE-180)";

	public static final String GET_ALL_REQUESTS = "SELECT * from SERVICE_TRACKER WHERE ACCOUNT_ID=?";
	
	public static final String GET_DETAILED_STATEMENT = "SELECT * from Transactions where Account_ID=? AND Date_of_Transaction>=? AND Date_of_Transaction<=?";

	public static final String GET_FORGOT_PASSWORD_OBJECT = "select * from user_table where user_id=?";

	public static final String SET_ONE_TIME_PASSWORD = "update user_table set login_password=? where user_id=?";

	public static final String GET_ACCOUNT_ID = "select account_id from account_master where user_id=?";


}
