
package com.cg.obs.dao;

public interface IQueryMapper {

	public static final String ADMIN_CREDENTIALS = "SELECT admin_id,user_id,password FROM admin_table WHERE user_id=?";
	
	public static final String USER_CREDENTIALS= "SELECT user_id,login_password,account_id,lock_status FROM user_table WHERE user_id=?";

	public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO Customer VALUES (?,?,?,?,?,?)";

	public static final String INSERT_ACCOUNT_MASTER = "INSERT INTO Account_Master VALUES (?,?,?,?)";

	public static final String GET_TRANSACTION_DETAILS = "SELECT * from Transactions where DateofTransaction>=? AND DateofTransaction<=?";

	public static final String GET_CUSTOMER_DETAILS = "SELECT * from Customer where USER_ID=?";
	
	public static final String UPDATE_CUSTOMER_DETAILS = "UPDATE CUSTOMER SET MOBILE=?,ADDRESS=? WHERE USER_ID=?";

	public static final String CHECK_OLD_PASSWORD = "SELECT LOGIN_PASSWORD FROM USER_TABLE WHERE USER_ID=?";

	public static final String UPDATE_CUSTOMER_PASSWORD = "UPDATE USER_TABLE SET LOGIN_PASSWORD=? WHERE USER_ID=?";
	
	public static final String GET_LOCK_STATUS = "SELECT lock_status from User_Table where Account_ID=?";

	public static final String CHANGE_ACCOUNT_STATUS = "UPDATE USER_TABLE set lock_status=? where Account_ID=?";

	public static final String LOCK_USER = "UPDATE user_table SET lock_status='l' WHERE user_id=?";

	public static final String GET_USER_ID = "SELECT user_id FROM user_table WHERE user_id=?";

	public static final String GET_USER_PASS = "SELECT login_password FROM user_table WHERE user_id=?";;;

	public static final String GENERATE_SERVICE_REQUEST = "INSERT INTO SERVICE_TRACKER VALUES(service.nextval,?,?,?,?)";

	public static final String GET_SERVICE_REQUEST_NUMBER = "SELECT service.currval from dual";
	
	public static final String GET_MINI_STATEMENT = "SELECT * from Transactions where Account_Id=?";

	public static final String GET_REQUEST_STATUS = "select * from service_tracker s where s.service_id=? and s.account_id in (select a.account_id from account_master a where a.user_id=?)";

	public static final String GET_ALL_REQUESTS = "SELECT * from SERVICE_TRACKER WHERE ACCOUNT_ID=? AND SERVICE_RAISED_DATE>(SYSDATE-180)";
	
	public static final String GET_DETAILED_STATEMENT = "SELECT * from Transactions where Account_ID=? AND DateofTransaction>=? AND DateofTransaction<=?";

	public static final String GET_ALL_ACCOUNTS = "SELECT ACCOUNT_ID FROM ACCOUNT_MASTER WHERE USER_ID=?";

	public static final String IS_NEW_USER = "SELECT Transaction_password from USER_TABLE WHERE USER_ID=?";

	public static final String COMPLETE_USER_PROFILE = "UPDATE USER_TABLE SET transaction_password=?,secret_question=?,secret_answer=? WHERE USER_ID=?";


}
