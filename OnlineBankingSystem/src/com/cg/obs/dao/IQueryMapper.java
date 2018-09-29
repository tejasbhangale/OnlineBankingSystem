package com.cg.obs.dao;

public interface IQueryMapper {
	
	public static final String ADMIN_CREDENTIALS= "SELECT admin_id,user_id,password FROM admin_table WHERE user_id=?";
}
