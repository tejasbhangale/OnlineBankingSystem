package com.cg.obs.bean;

public class Customer {

	private int accountId;
	private String customerName;
	private int mobile;
	private String email;
	private String address;
	private String pancard ;
	
	@Override
	public String toString() {
		return "Customer [accountId=" + accountId + ", customerName="
				+ customerName + ", mobile=" + mobile + ", email=" + email
				+ ", address=" + address + ", pancard=" + pancard + "]";
	}
	public Customer() {
		super();
		
	}
	public Customer(int accountId, String customerName, int mobile,
			String email, String address, String pancard) {
		super();
		this.accountId = accountId;
		this.customerName = customerName;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.pancard = pancard;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
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
