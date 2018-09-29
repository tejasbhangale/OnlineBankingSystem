package com.cg.obs.dao;

public interface IQueryMapper {
	
	public static final String ADMIN_CREDENTIALS= "SELECT admin_id,user_id,password FROM admin_table WHERE user_id=?";
	public static final String GET_CUSTOMER_DETAILS = "Select * from Customer where Account_ID=?";
	public static final String UPDATE_CUSTOMER_DETAILS = "UPDATE Customer set mobile=?,address=? where Account_ID=?";
	
}
