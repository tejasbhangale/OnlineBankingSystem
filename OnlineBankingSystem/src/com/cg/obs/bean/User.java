package com.cg.obs.bean;

/**
 * @author tbhangal
 *
 */
public class User {

	private int accountId;
	private long userId;
	private String loginPassword;
	private String secretQuestion;
	private String secretAnswer;
	private String transactionPassword;
	private String lockStatus;
	
	
	@Override
	public String toString() {
		return "User [accountId=" + accountId + ", userId=" + userId
				+ ", loginPassword=" + loginPassword + ", secretQuestion="
				+ secretQuestion + ", secretAnswer=" + secretAnswer
				+ ", transactionPassword=" + transactionPassword
				+ ", lockStatus=" + lockStatus + "]";
	}
	public User(int accountId, long userId, String loginPassword,
			String secretQuestion, String secretAnswer,
			String transactionPassword, String lockStatus) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.loginPassword = loginPassword;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.transactionPassword = transactionPassword;
		this.lockStatus = lockStatus;
	}
	public User() {
		super();
	}
	public String getSecretAnswer() {
		return secretAnswer;
	}
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
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
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	
	
	
}
