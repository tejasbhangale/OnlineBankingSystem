package com.cg.obs.bean;

import java.sql.Date;

public class FundTransfer {

	private long fundTransferId;
	private long accountId;
	private long payeeAccountId;
	private Date dateOfTransfer;
	private double transferAmount;
	
	
	
	public FundTransfer() {
		super();
	}


	public FundTransfer(long fundTransferId, long accountId,
			long payeeAccountId, Date dateOfTransfer, double transferAmount) {
		super();
		this.fundTransferId = fundTransferId;
		this.accountId = accountId;
		this.payeeAccountId = payeeAccountId;
		this.dateOfTransfer = dateOfTransfer;
		this.transferAmount = transferAmount;
	}


	public long getFundTransferId() {
		return fundTransferId;
	}


	public void setFundTransferId(long fundTransferId) {
		this.fundTransferId = fundTransferId;
	}


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public long getPayeeAccountId() {
		return payeeAccountId;
	}


	public void setPayeeAccountId(long payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}


	public Date getDateOfTransfer() {
		return dateOfTransfer;
	}


	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}


	public double getTransferAmount() {
		return transferAmount;
	}


	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}


	@Override
	public String toString() {
		return "FundTransfer [fundTransferId=" + fundTransferId
				+ ", accountId=" + accountId + ", payeeAccountId="
				+ payeeAccountId + ", dateOfTransfer=" + dateOfTransfer
				+ ", transferAmount=" + transferAmount + "]";
	}
	
	
    
}

