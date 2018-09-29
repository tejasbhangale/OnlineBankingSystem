package com.cg.obs.bean;

/**
 * @author tbhangal
 *
 */
public class User {

	private int accountId;
	private int userId;
	private String loginPassword;
	private String secretQuestion;
	private String transactionPassword;
	private char lockStatus;
	
	@Override
	public String toString() {
		return "User [accountId=" + accountId + ", userId=" + userId
				+ ", loginPassword=" + loginPassword + ", secretQuestion="
				+ secretQuestion + ", transactionPassword="
				+ transactionPassword + ", lockStatus=" + lockStatus + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int accountId, int userId, String loginPassword,
			String secretQuestion, String transactionPassword, char lockStatus) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.loginPassword = loginPassword;
		this.secretQuestion = secretQuestion;
		this.transactionPassword = transactionPassword;
		this.lockStatus = lockStatus;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getSecretQuestion() {
		return secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	public String getTransactionPassword() {
		return transactionPassword;
	}
	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}
	public char getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(char lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	
	
}
