package com.cg.obs.bean;

public class Customer {
	
	private long userId;
	private String customerName;
	private long mobile;
	private String email;
	private String address;
	private String pancard ;
	
	@Override
	public String toString() {
		return "Customer [accountId=" + userId + ", customerName="
				+ customerName + ", mobile=" + mobile + ", email=" + email
				+ ", address=" + address + ", pancard=" + pancard + "]";
	}
	public Customer() {
		super();
		
	}
	public Customer(int userId, String customerName, long mobile,
			String email, String address, String pancard) {
		super();
		this.userId = userId;
		this.customerName = customerName;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.pancard = pancard;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String User() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPancard() {
		return pancard;
	}
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	
	
}
