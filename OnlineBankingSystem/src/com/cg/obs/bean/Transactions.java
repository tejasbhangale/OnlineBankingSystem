package com.cg.obs.bean;

import java.sql.Date;

public class Transactions {
	private long transactionId;
	private String transactionDesc;
	private Date dateOfTransaction;
	private String transactionType;
	private double transactionAmount;
    private long accountId;
    
	public Transactions() {
		super();
		
	}

	public Transactions(long transactionId, String transactionDesc,
			Date dateOfTransaction, String transactionType,
			double transactionAmount, long accountId) {
		super();
		this.transactionId = transactionId;
		this.transactionDesc = transactionDesc;
		this.dateOfTransaction = dateOfTransaction;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.accountId = accountId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Transactions [transactionId=" + transactionId
				+ ", transactionDesc=" + transactionDesc
				+ ", dateOfTransaction=" + dateOfTransaction
				+ ", transactionType=" + transactionType
				+ ", transactionAmount=" + transactionAmount + ", accountId="
				+ accountId + "]";
	}
    
    
    
    
	
}
