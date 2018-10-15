package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class CustomerDaoImpl implements ICustomerDao {

	Logger logger=Logger.getRootLogger();
	public CustomerDaoImpl()
	{
	PropertyConfigurator.configure("res//log4j.properties");
	
	}
	/*******************************************************************************************************
	 - Function Name	:	getCustomerDetails(long id)
	 - Input Parameters	:	long id
	 - Return Type		:	Customer
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Returns Customer Details
	 ********************************************************************************************************/
	@Override
	public Customer getCustomerDetails(long id) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_CUSTOMER_DETAILS);) {
			pt.setLong(1, id);
			ResultSet cusResSet = pt.executeQuery();

			while (cusResSet.next()) {
				Customer customer = new Customer();
				customer.setUserId(id);
				customer.setCustomerName(cusResSet.getString(2));
				customer.setMobile(cusResSet.getLong(3));
				customer.setEmail(cusResSet.getString(4));
				customer.setAddress(cusResSet.getString(5));
				customer.setPancard(cusResSet.getString(6));
				return customer;
			}

		} catch (SQLException e) {
			logger.error("Error fetching Customer Details"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}

		return null;
	}
	
	/*******************************************************************************************************
	 - Function Name	:	updateCustomerDetails(Customer customer)
	 - Input Parameters	:	Customer customer
	 - Return Type		:	boolean
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Updates customer details
	 ********************************************************************************************************/

	@Override
	public boolean updateCustomerDetails(Customer customer) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_DETAILS);) {
			pt.setLong(1, customer.getMobile());
			pt.setString(2, customer.getAddress());
			pt.setLong(3, customer.getUserId());

			int res = pt.executeUpdate();
			if (res >= 1){
				logger.info("Updated Customer Details");
				return true;

			}
			
		} catch (SQLException e) {
			logger.error("Error Updating Customer Details"+e.getMessage());
			throw new OnlineBankingException(Messages.CUSTOMER_UPDATE_FAILED_DAO);
		}
		return false;

	}

	@Override
	public boolean checkOldPass(String oldPass, long userId) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.CHECK_OLD_PASSWORD);) {
			pt.setLong(1, userId);

			ResultSet res = pt.executeQuery();
			if (res.next()) {
				if (res.getString(1).equals(oldPass)) {
					
					return true;
				}
			}

		} catch (SQLException e) {
			logger.error("Error Validating Old Password"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
		return false;
	}
	
	/*******************************************************************************************************
	 - Function Name	:	updatePassword(String newPass, long userId)
	 - Input Parameters	:	String newPass, long userId
	 - Return Type		:	void
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Updates password
	 ********************************************************************************************************/

	@Override
	public void updatePassword(String newPass, long userId)
			throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_PASSWORD);) {

			pt.setString(1, newPass);

		
			pt.setLong(2, userId);
		

			if(pt.executeUpdate()>0){
				logger.info("Updated Customer Password");
			}

			
		}  catch (SQLException e) {
			logger.error("Error Updating Password"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
	}

	/*******************************************************************************************************
	 - Function Name	: requestChequeBook(long accId)
	 - Input Parameters	: long accId
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Returns Checkbook Request ID
	 ********************************************************************************************************/
	@Override
	public int requestChequeBook(long accId) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GENERATE_SERVICE_REQUEST);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_SERVICE_REQUEST_NUMBER);) {

			Date date = Date.valueOf(LocalDate.now());
			pt.setString(1, "New ChequeBook Request");
			pt.setLong(2, accId);
			pt.setDate(3, date);
			pt.setString(4, "Issued");

			int result = pt.executeUpdate();

			if (result >= 1) {
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					logger.info("Placed ChequeBook Request");
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (SQLException e) {
			logger.error("Error Requesting Cheque Book"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
	}

	@Override
	public List<Integer> getAccountList(long id) throws OnlineBankingException {

		List<Integer> accountList = new ArrayList<Integer>();
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_SELF_ACCOUNTS);) {
			pt.setLong(1, id);

			ResultSet resultSet = pt.executeQuery();

			while (resultSet.next()) {
				
				accountList.add(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			logger.error("Error Fetching Accounts List"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		} 
		return accountList;
	}
	/*******************************************************************************************************
	 - Function Name	: getMiniStatement(long accNum) 
	 - Input Parameters	: long accNum
	 - Return Type		: List<Transactions>
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Returns first 10 transactions for Mini Statement
	 ********************************************************************************************************/
	@Override
	public List<Transactions> getMiniStatement(long accId)
			throws OnlineBankingException {

		List<Transactions> transaction = new ArrayList<>();

		int count = 1;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_MINI_STATEMENT);) {

			pstm.setLong(1, accId);

			ResultSet result = pstm.executeQuery();

			while (result.next() && count <= 2) {
				Transactions tran = new Transactions();

				tran.setTransactionId(result.getLong(1));
				tran.setTransactionDesc(result.getString(2));
				tran.setDateOfTransaction(result.getDate(3));
				tran.setTransactionType(result.getString(4));
				tran.setTransactionAmount(result.getDouble(5));
				tran.setAccountId(result.getLong(6));
				transaction.add(tran);
				count++;
			}
		} catch (SQLException e) {
			logger.error("Error Fetching Mini Statement"+e.getMessage());
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);
		}
		if (count == 1) {
			return null;
		}

		return transaction;
	}
	
	/*******************************************************************************************************
	 - Function Name	: getRequestStatus(int reqNum, int userId)
	 - Input Parameters	: int reqNum, int userId
	 - Return Type		: ServiceTracker
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Returns Service Tracker details
	 ********************************************************************************************************/

	public ServiceTracker getRequestStatus(int reqNum, long userId) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_REQUEST_STATUS);) {

			pt.setInt(1, reqNum);
			pt.setLong(2, userId);

			ServiceTracker sTrack = null;
			ResultSet res = pt.executeQuery();
			while (res.next()) {
				sTrack = new ServiceTracker();
				sTrack.setService_id(res.getInt(1));
				sTrack.setServiceDescription(res.getString(2));
				sTrack.setAccountId(res.getLong(3));
				sTrack.setServiceRaisedDate(res.getDate(4));
				sTrack.setStatus(res.getString(5));
			}
			return sTrack;

		} catch (SQLException e) {
			logger.error("Error Fetching RequestStatus"+e.getMessage());
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(long accNum) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_REQUESTS);) {

			pt.setLong(1, accNum);

			ResultSet resSet = pt.executeQuery();
			ArrayList<ServiceTracker> reqList = new ArrayList<ServiceTracker>();
			int count = 0;
			while (resSet.next() && count < 20) {
				count++;
				ServiceTracker sTrack = new ServiceTracker();
				sTrack.setService_id(resSet.getInt(1));
				sTrack.setServiceDescription(resSet.getString(2));
				sTrack.setAccountId(resSet.getLong(3));
				sTrack.setServiceRaisedDate(resSet.getDate(4));
				sTrack.setStatus(resSet.getString(5));
				reqList.add(sTrack);
			}
			return reqList;
		} catch (SQLException e) {
			throw new OnlineBankingException(
					Messages.DATABASE_ERROR);
		}

	}

	
	/*******************************************************************************************************
	 - Function Name	: getDetailedStatement(long accNum, Date startDate, Date endDate)
	 - Input Parameters	: long accNum, Date startDate,Date endDate
	 - Return Type		: List<Transactions>
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Returns Detailed Statement
	 ********************************************************************************************************/
	@Override
	public List<Transactions> getDetailedStatement(long accNum, Date startDate,
			Date endDate) throws OnlineBankingException {

		List<Transactions> transaction = new ArrayList<>();

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_DETAILED_STATEMENT);) {

			pstm.setLong(1, accNum);
			pstm.setDate(2, startDate);
			pstm.setDate(3, endDate);

			ResultSet result = pstm.executeQuery();

			while (result.next()) {
				Transactions tran = new Transactions();

				tran.setTransactionId(result.getLong(1));
				tran.setTransactionDesc(result.getString(2));
				tran.setDateOfTransaction(result.getDate(3));
				tran.setTransactionType(result.getString(4));
				tran.setTransactionAmount(result.getDouble(5));
				tran.setAccountId(result.getLong(6));
				transaction.add(tran);

			}

		} catch (SQLException e) {
			logger.error("Error Fetching Detailed Statement"+e.getMessage());
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);
		}

		return transaction;
	}

	@Override
	public double getAccBalance(long accountId) throws OnlineBankingException {
		double balance = 0;
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_ACCOUNT_BALANCE);) {
			pstm.setLong(1, accountId);
			ResultSet resultSet = pstm.executeQuery();
			if (resultSet.next()) {
				balance = resultSet.getDouble(1);
			}

		} catch (SQLException e ) {
			throw new OnlineBankingException(Messages.DATABASE_ERROR);
		}
		return balance;
	}

	@Override
	public List<Payee> getPayeeList(long id) throws OnlineBankingException {
		List<Payee> payeeList = new ArrayList<Payee>();
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_PAYEE_LIST);) {
			pt.setLong(1, id);

			ResultSet resultSet = pt.executeQuery();

			while (resultSet.next()) {
				Payee payee = new Payee();
				payee.setAccountId(resultSet.getLong(1));
				payee.setPayeeAccountId(resultSet.getLong(2));
				payee.setNickName(resultSet.getString(3));
				payeeList.add(payee);
			}

		} catch (SQLException e) {
			logger.error("Error Fetching Payee List"+e.getMessage());
			throw new OnlineBankingException(Messages.PAYEELIST_FETCH);
		} 
		return payeeList;
	}
	
	/*******************************************************************************************************
	 - Function Name	:	debitFunds(long accountID, double transferAmount) 
	 - Input Parameters	:	long accountID, double transferAmount
	 - Return Type		:	boolean
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Debits funds from the payer's account
	 ********************************************************************************************************/

	
	private boolean debitFunds(Connection connection, long accountID, double transferAmount) throws OnlineBankingException {
		try (PreparedStatement pt = connection
						.prepareStatement(IQueryMapper.DEBIT_FUNDS);) {
			pt.setDouble(1, transferAmount);
			pt.setLong(2, accountID);

			int res = pt.executeUpdate();
			if (res == 1){
				logger.info("Account Debit Success for :"+accountID);
				return true;
			}
		} catch (SQLException e) {
			logger.error("Error Debiting Funds"+e.getMessage());
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		}
		return false;
	}

	/*******************************************************************************************************
	 - Function Name	:	getAllAccounts(int userId)
	 - Input Parameters	:	int userId
	 - Return Type		:	ArrayList<Integer>
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Returns List of all the bank accounts of a user
	 ********************************************************************************************************/
	@Override
	public ArrayList<Integer> getAllAccounts(long userId) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_ACCOUNTS);) {

			pt.setLong(1, userId);

			ResultSet resSet = pt.executeQuery();
			ArrayList<Integer> accList = new ArrayList<Integer>();
			while (resSet.next()) {
			
				accList.add(resSet.getInt(1));
			}
			return accList;

		} catch (SQLException e) {
			
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		}

	}
	
	/*******************************************************************************************************
	 - Function Name	: creditFunds(long accountID, double transferAmount)
	 - Input Parameters	: long accountID, double transferAmount
	 - Return Type		: boolean
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Credits Funds to  payee acount
	 * @param connection 
	 ********************************************************************************************************/

	private boolean creditFunds(Connection connection, long accountID, double transferAmount) throws OnlineBankingException {
		try (PreparedStatement pt = connection
						.prepareStatement(IQueryMapper.CREDIT_FUNDS);) {
			pt.setDouble(1, transferAmount);
			pt.setLong(2, accountID);

			int res = pt.executeUpdate();
			if (res == 1) {
				logger.info("Account Credit Success for :"+accountID);
				return true;
			}
		} catch (SQLException e) {
			logger.error("Error Crediting Funds"+e.getMessage());
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		}
		return false;
	}
	
	
	
	/*******************************************************************************************************
	 - Function Name	: recordFundTransfer(long fromaccount, long toaccount,double transferAmount)
	 - Input Parameters	: long fromaccount, long toaccount, double transferAmount
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Returns Fund Transfer Id 
	 ********************************************************************************************************/

	@Override
	public int recordFundTransfer(long fromaccount, long toaccount,
			double transferAmount) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt1 = conn
						.prepareStatement(IQueryMapper.RECORD_FUND_TRANSFER);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_FUND_TRANSFER_ID);) {
			pt1.setLong(1, fromaccount);
			pt1.setLong(2, toaccount);
			pt1.setDouble(3, transferAmount);

			int result = pt1.executeUpdate();
			if (result == 1) {
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					logger.info("Record Fund Transfer success");
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			} 
			} catch (SQLException e) {
				logger.error("Error Transferring Funds"+e.getMessage());
				throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
			}
		}

	@Override
	public boolean isFirstTimeUser(long userId) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.IS_NEW_USER);) {

			pt.setLong(1, userId);

			ResultSet resSet = pt.executeQuery();

			if (resSet.next()) {
				if (resSet.getString(1)==null)
					return true;
				return false;
			} else {
				return true;
			}
		}catch (SQLException e) {
			throw new OnlineBankingException(Messages.FIRST_TIME_USER);
		}

	}

	/*******************************************************************************************************
	 - Function Name	: recordTransaction(long accountId, int fundTransferId,
						  String type, double transferAmount)
	 - Input Parameters	: long accountId, String transDesc,
						  String type, double transferAmount
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: Records transaction
	 ********************************************************************************************************/

	
	private int recordTransaction(long accountId, String transDesc,
			String type, double transferAmount) throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt1 = conn
						.prepareStatement(IQueryMapper.RECORD_TRANSACTION);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_TRANSACTION_ID);) {
			pt1.setString(1, transDesc);
			pt1.setString(2, type);
			pt1.setDouble(3, transferAmount);
			pt1.setLong(4, accountId);

			int result = pt1.executeUpdate();
			if (result == 1) {
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					logger.info("Transaction Record success");
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (SQLException e) {
			logger.error("Error Recording Transaction"+e.getMessage());
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		}
	}

	/*******************************************************************************************************
	 - Function Name	:	addPayee(Payee payee)
	 - Input Parameters	:	Payee payee
	 - Return Type		:	void
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Adds Beneficiary to the user account
	 ********************************************************************************************************/
	@Override
	public void addPayee(Payee payee) throws OnlineBankingException {
		
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.ADD_PAYEE);) {
			pt.setLong(1, payee.getAccountId());
			pt.setLong(2, payee.getPayeeAccountId());
			pt.setString(3, payee.getNickName());
			if(pt.executeUpdate()>0){
				logger.info("Payee Added Successfully");
			}
		} catch (SQLException e) {
			logger.error("Error Adding Payee"+e.getMessage());
			throw new OnlineBankingException(Messages.SQL_ADD_PAYEE);
		}
	}

	@Override
	public boolean isValidAccount(long accountId) throws OnlineBankingException {
		boolean payeeExist=false;
		try (Connection connection = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = connection
						.prepareStatement(IQueryMapper.VALID_ACCOUNT);) {
			pt.setLong(1, accountId);
			ResultSet resultSet = pt.executeQuery();
			payeeExist=resultSet.next();
		} catch (SQLException e) {
			logger.error("Error Adding Payee"+e.getMessage());
			throw new OnlineBankingException(Messages.SQL_ADD_PAYEE);
		}
		return payeeExist;
	}
	

	/*******************************************************************************************************
	 - Function Name	:	getUserTransPassword(long userId)
	 - Input Parameters	:	long userId
	 - Return Type		:	String
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Retrieves transaction password for the requested user
	 ********************************************************************************************************/
	@Override
	public String getUserTransPassword(long userId) throws OnlineBankingException {
		String pass=null;
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_TRANSACTION_PASSWORD);) {
			
			pt.setLong(1,userId);
			
			ResultSet resultset = pt.executeQuery();
			if(resultset.next()){
				pass=resultset.getString(1);
				
			}
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.TRANS_PASSWORD);
		} 
		return pass;

	}
	
	/*******************************************************************************************************
	 - Function Name	:	completeProfile(ArrayList<String> userData, int userId)
	 - Input Parameters	:	ArrayList<String> userData, int userId
	 - Return Type		:	void
	 - Throws			:  	OnlineBankingException
	 - Author			:	CAPGEMINI
	 - Description		:	Completes User Profile on First Time login
	 ********************************************************************************************************/

	@Override
	public void completeProfile(ArrayList<String> userData, long userId) throws OnlineBankingException{
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.COMPLETE_USER_PROFILE);) {

			pt.setString(1, userData.get(1));
			pt.setString(2, userData.get(2));
			pt.setString(3, userData.get(3));
			pt.setLong(4, userId);
			
			if(pt.executeUpdate()>0){
				logger.info("First Time User Profile Completed");
			}
		
		} catch (SQLException e) {
			logger.error("Error Completing Profile"+e.getMessage());
			throw new OnlineBankingException(Messages.UPDATE_FAILED);
		}

	}
	
	/*******************************************************************************************************
	 - Function Name	: transferfunds(long fromaccount, long toaccount, double transferAmount)
	 - Input Parameters	: long fromaccount, long toaccount, double transferAmount
	 - Return Type		: int
	 - Throws		    : OnlineBankingException
	 - Author	      	: CAPGEMINI
	 - Description		: generate Funds transfer,transaction id and debit and credit funds from accounts
	 ********************************************************************************************************/

	@Override
	public int transferfunds(long fromaccount, long toaccount,
			double transferAmount) throws OnlineBankingException {
		Connection connection = null ;
		boolean debitSuccess = false;
		boolean creditSuccess = false;
		int fundTransferId = 0;
		int fromTransactionId = 0;
		try {
			fundTransferId=recordFundTransfer(fromaccount, toaccount, transferAmount);
			String tranDesc= ("FT:" + fundTransferId);
			fromTransactionId=recordTransaction(fromaccount, tranDesc, "d", transferAmount);

			connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
			connection.setAutoCommit(false);

			debitSuccess=debitFunds(connection,fromaccount, transferAmount);
			creditSuccess=creditFunds(connection,toaccount, transferAmount);
			if(creditSuccess){
				recordTransaction(toaccount, tranDesc, "c", transferAmount);
			}

		} catch (OnlineBankingException | SQLException e) {
			try {
				String failedDesc=("FT:" + fundTransferId+" REVERTED");
				if(debitSuccess && !creditSuccess){
					connection.rollback();

					fromTransactionId=recordTransaction(fromaccount, failedDesc, "c", transferAmount);
				}

			} catch (SQLException e1) {
				throw new OnlineBankingException(Messages.FUNDS_ROLLBACK);
			}
			throw new OnlineBankingException(Messages.FUNDS_TRANSFER_ERROR);
		} finally{
			try {
				connection.commit();
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				throw new OnlineBankingException(Messages.DB_CONNECTION);
			}
		}


		return fromTransactionId;
	}
	
	
}
