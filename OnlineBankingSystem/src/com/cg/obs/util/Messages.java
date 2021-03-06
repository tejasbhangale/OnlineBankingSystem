package com.cg.obs.util;

public class Messages {
	
	/*
	 * Connection Configuration Messages
	 */
	public static final String CONNECTION_CONFIGURATION_FAILURE="Connection Configuration Not Loaded!";
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
	
	//Payee update failed 
	public static final String PAYEE_UPDATE_FAILED = "Failed to add Payee!!! Please try afte some time";
	
	//Admin
	public static final String SQL_EXCEPTION_ERROR = "Error while fetching data";
	
	// SQL Exception
	public static final String SQL_ADD_PAYEE= "Failed to add Payee!!! Please try after some time";
	public static final String TRANS_PASSWORD = "Failed to verify Transaction Password!!! Please try after some time";
	public static final String RECORD_TRANSACTION = "Transaction cannot be recored. System Issue!!!";
	public static final String FIRST_TIME_USER = "Unable to perform action!!! Please Try after some time";
	public static final String DATABASE_ERROR = "Unable to perform action!!! Please try after some time";
	public static final String FUNDS_TRANSFER_ERROR = "Unable to tranfer Funds!!! Please try after some time";
	public static final String PAYEELIST_FETCH = "Failed to get Payee list!!! Please try after some time";
	public static final String FORGOT_PASSWORD = "Unable to change password due to server issue!!! Please try after some time ";
	public static final String CHANGE_LOCK_STATUS = "Unable to change status due to server issue!!! Please try after some time ";
	public static final String DB_CONNECTION = "Technical issue occured!!! Check after some time";
	public static final String FUNDS_ROLLBACK = "Technical issue occured!!! Unable to transfer/revert back funds.Please try after some time";
	public static final String PAYEE_ACCOUNT_ID_INVALID = "Payee account does not exists!!!";
	public static final String INVALID_TRANSFER_AMOUNT = "Invalid Transfer Amount Entered";

}

