package com.cg.obs.bean;

public class Admin {
	
	private int adminId;
	private String userId;
	private String password;
	
	
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", userId=" + userId
				+ ", password=" + password + "]";
	}
	public Admin(int adminId, String userId, String password) {
		super();
		this.adminId = adminId;
		this.userId = userId;
		this.password = password;
	}
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
