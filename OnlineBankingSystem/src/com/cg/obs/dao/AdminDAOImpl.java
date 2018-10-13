package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.ui.ClientMain;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class AdminDAOImpl implements IAdminDAO {

	private Logger log = Logger.getLogger("xcv");

	public AdminDAOImpl() {

		PropertyConfigurator.configure("res/log4j.properties");

	}

	@Override
	public long createNewUser() throws OnlineBankingException {

		Long userId = null;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm1 = conn
						.prepareStatement(IQueryMapper.GET_USER_ID_SEQ);
				PreparedStatement pstm2 = conn
						.prepareStatement(IQueryMapper.INSERT_USER_TABLE);) {

			ResultSet result = pstm1.executeQuery();

			while (result.next()) {
				userId = result.getLong(1);
			}

			pstm2.setLong(1, userId);
			pstm2.setString(2, null);
			pstm2.setString(3, null);
			pstm2.setString(4, null);
			pstm2.setString(5, null);
			pstm2.setString(6, "u");

			pstm2.executeUpdate();

		} catch (SQLException e) {

			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		return userId;
	}

	@Override
	public boolean addAccountDetails(Customer cust)
			throws OnlineBankingException {

		int status = 0;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.INSERT_ACCOUNT_DETAILS);) {

			pstm.setLong(1, cust.getUserId());
			pstm.setString(2, cust.getCustomerName());
			pstm.setLong(3, cust.getMobile());
			pstm.setString(4, cust.getEmail());
			pstm.setString(5, cust.getAddress());
			pstm.setString(6, cust.getPancard());

			status = pstm.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		if (status > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean addAccountMaster(AccountMaster account)
			throws OnlineBankingException {

		int status = 0;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm1 = conn
						.prepareStatement(IQueryMapper.GET_ACCOUNT_NUMBER);
				PreparedStatement pstm2 = conn
						.prepareStatement(IQueryMapper.INSERT_ACCOUNT_MASTER);) {

			ResultSet result = pstm1.executeQuery();

			while (result.next()) {

				account.setAccountId(result.getLong(1));

			}

			pstm2.setLong(1, account.getAccountId());
			pstm2.setLong(2, account.getUserId());
			pstm2.setString(3, account.getAccountType());
			pstm2.setDouble(4, account.getOpeningBalance());
			pstm2.setDate(5, (Date) account.getOpenDate());

			status = pstm2.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		if (status > 0) {
			return true;
		}

		return false;
	}

	@Override
	public List<Transactions> getTransactionDetails(Date startDate, Date endDate)
			throws OnlineBankingException {

		List<Transactions> list = new ArrayList<Transactions>();

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_TRANSACTION_DETAILS);) {

			pstm.setDate(1, startDate);
			pstm.setDate(2, endDate);

			ResultSet result = pstm.executeQuery();

			while (result.next()) {
				Transactions tran = new Transactions();

				tran.setTransactionId(result.getLong(1));
				tran.setTransactionDesc(result.getString(2));
				tran.setDateOfTransaction(result.getDate(3));
				tran.setTransactionType(result.getString(4));
				tran.setTransactionAmount(result.getDouble(5));
				tran.setAccountId(result.getLong(6));
				list.add(tran);

			}

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		return list;
	}

	@Override
	public boolean changeAccountStatus(int accNumber, String status)
			throws OnlineBankingException {

		int check = 0;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.CHANGE_ACCOUNT_STATUS);) {

			pstm.setString(1, status);
			pstm.setInt(2, accNumber);

			check = pstm.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		if (check == 1) {
			return true;
		}

		return false;
	}

	@Override
	public String getLockStatus(int accNumber) throws OnlineBankingException {

		String status = null;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_LOCK_STATUS);) {

			pstm.setInt(1, accNumber);

			ResultSet result = pstm.executeQuery();

			while (result.next()) {
				status = result.getString(1);

			}

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}

		return status;
	}

	@Override
	public Customer getCustomerDetails(int accNumber)
			throws OnlineBankingException {

		Customer customer = null;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_CUSTOMER_DETAILS);) {

			pstm.setInt(1, accNumber);

			ResultSet result = pstm.executeQuery();

			while (result.next()) {
				customer = new Customer();

				customer.setCustomerName(result.getString(2));
				customer.setMobile(result.getLong(3));
				customer.setEmail(result.getString(4));
				customer.setAddress(result.getString(5));

			}

		} catch (SQLException e) {
			log.error("SQL Exception occured");
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);

		}
		return customer;
	}

}
