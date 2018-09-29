package com.cg.obs.bean;

import java.sql.Date;





public class AccountMaster {

	long accountId;
	String accountType;
	double openingBalance;
	Date openDate;
	
	
	
	
	public AccountMaster() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AccountMaster(long accountId, String accountType,
			double openingBalance, Date openDate) {
		super();
		this.accountId = accountId;
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		this.openDate = openDate;
	}


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public double getOpeningBalance() {
		return openingBalance;
	}


	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}


	public Date getOpenDate() {
		return openDate;
	}


	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}


	@Override
	public String toString() {
		return "AccountMaster [accountId=" + accountId + ", accountType="
				+ accountType + ", openingBalance=" + openingBalance
				+ ", openDate=" + openDate + "]";
	}
	
	
	
}
