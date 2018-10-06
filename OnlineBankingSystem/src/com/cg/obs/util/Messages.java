package com.cg.obs.util;

public class Messages {
	
	/*
	 * Connection Configuration Messages
	 */
	public static final String CONNECTION_CONFIGURATION_FAILURE="Connection Configuaration Not Loaded!";
	public static final String CONNECTION_ESTABILISHED_FAILURE="Connection is not established!";
	/*
	 * Login-Module Messages
	 */
	public static final String INVALID_USERNAME="No Such UserName";
	public static final String INVALID_PASSWORD="Password Mismatch";
	public static final String ACCOUNT_LOCKED = "Your Account is Locked. Kindly Contact Bank Admin";
	/*
	 * User-Client Messages
	 */
	//choice
	public static final String INCORRECT_INPUT_TYPE = "Please Enter Option as 'number' only!";
	public static final String INCORRECT_CHOICE = "Incorrect choice entered! Try Again.";
	//update mobile/address
	public static final String INCORRECT_MOBILE_NUMBER = "Please Enter Correct mobile number";
	public static final String INCORRECT_CUSTOMER_ADDRESS = "Please Enter Valid ADDRESS";
	public static final String INVALID_MOBILE_FORMAT = "Invalid Mobile format, Enter numbers only!";
	public static final String CUSTOMER_UPDATE_SUCCESS = "Your Details have been successfully updated!";
	public static final String CUSTOMER_UPDATE_FAILED_CLIENT = "Sorry!, Your details could not be updated. Please try again.";
	public static final String CUSTOMER_UPDATE_FAILED_DAO = "Unexpected Error! Failed to update Details. Please try again!";
	//update password
	public static final String INVALID_OLD_PASS = "Your old-password is invalid!";
	public static final String PASSWORD_UPDATE_FAILED = "Error while updating Password!";
	public static final String PASSWORD_UPDATE_SUCCESS = "Your password has been successfully updated!";
	public static final String MAXIMUM_NEWPASS_TRIES = "Maximum number of tries exceeded! Please try again.";
	//chequebook
	public static final String CHEQUEBOOK_SUCCESS = "Your request for ChequeBook has been Approved.";
	public static final String SERVICE_REQUEST_FAILED = "Your request for ChequeBook has been declined.";
	//update exception
	public static final String UPDATE_FAILED = "Update request failed!";
	//exit
	public static final String EXIT_MESSAGE = "Thank you for using ONLINE BANKING SYSTEM!!!";
}

