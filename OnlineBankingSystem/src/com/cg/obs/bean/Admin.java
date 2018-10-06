package com.cg.obs.bean;

public class Admin {
	
	private int adminId;
	private String adminUserId;
	private String adminPassword;
	public Admin(int adminId, String adminUserId, String adminPassword) {
		super();
		this.adminId = adminId;
		this.adminUserId = adminUserId;
		this.adminPassword = adminPassword;
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
	public String getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminUserId=" + adminUserId
				+ ", adminPassword=" + adminPassword + "]";
	}
	
	
	
	
	
}
