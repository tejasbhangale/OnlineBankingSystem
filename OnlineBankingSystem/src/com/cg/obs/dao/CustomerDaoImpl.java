package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.CompleteProfileException;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class CustomerDaoImpl implements ICustomerDao {

	@Override
	public Customer getCustomerDetails(int id) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_CUSTOMER_DETAILS);) {
			pt.setInt(1, id);
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

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateCustomerDetails(Customer customer) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_DETAILS);) {
			pt.setLong(1, customer.getMobile());
			pt.setString(2, customer.getAddress());
			pt.setInt(3, customer.getUserId());

			int res = pt.executeUpdate();
			if (res >= 1)
				return true;

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean checkOldPass(String oldPass, int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.CHECK_OLD_PASSWORD);) {
			pt.setInt(1, userId);

			ResultSet res = pt.executeQuery();
			if (res.next()) {
				if (res.getString(1).equals(oldPass)) {
					return true;
				}
			}

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePassword(String newPass, int userId)
			throws PasswordUpdateException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_PASSWORD);) {
			
			pt.setString(1, newPass);
			pt.setInt(2, userId);
			pt.executeUpdate();
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int requestChequeBook(int accId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GENERATE_SERVICE_REQUEST);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_SERVICE_REQUEST_NUMBER);) {

			Date date = Date.valueOf(LocalDate.now());
			pt.setString(1, "New ChequeBook Request");
			pt.setInt(2, accId);
			pt.setDate(3, date);
			pt.setString(4, "Issued");

			int result = pt.executeUpdate();

			if (result >= 1) {
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Transactions> getMiniStatement(int ar)
			throws JDBCConnectionError {

		List<Transactions> transaction = new ArrayList<>();

		int count = 1;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_MINI_STATEMENT);) {

			pstm.setInt(1, ar);

			ResultSet result = pstm.executeQuery();

			while (result.next() && count <= 10) {
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
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new JDBCConnectionError(
					Messages.CONNECTION_ESTABILISHED_FAILURE);
		}

		if (count == 1) {
			return null;
		}

		return transaction;
	}

	public ServiceTracker getRequestStatus(int reqNum, int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_REQUEST_STATUS);) {

			pt.setInt(1, reqNum);
			pt.setInt(2, userId);

			ServiceTracker sTrack = null;
			ResultSet res = pt.executeQuery();
			while (res.next()) {
				sTrack = new ServiceTracker();
				sTrack.setService_id(res.getInt(1));
				sTrack.setServiceDescription(res.getString(2));
				sTrack.setAccountId(res.getInt(3));
				sTrack.setServiceRaisedDate(res.getDate(4));
				sTrack.setStatus(res.getString(5));
			}
			return sTrack;

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_REQUESTS);) {

			pt.setInt(1, accNum);

			ResultSet resSet = pt.executeQuery();
			ArrayList<ServiceTracker> reqList = new ArrayList<ServiceTracker>();
			int count = 0;
			while (resSet.next() && count < 20) {
				count++;
				ServiceTracker sTrack = new ServiceTracker();
				sTrack.setService_id(resSet.getInt(1));
				sTrack.setServiceDescription(resSet.getString(2));
				sTrack.setAccountId(resSet.getInt(3));
				sTrack.setServiceRaisedDate(resSet.getDate(4));
				sTrack.setStatus(resSet.getString(5));
				reqList.add(sTrack);
			}
			return reqList;
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws JDBCConnectionError {

		List<Transactions> transaction = new ArrayList<>();

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_DETAILED_STATEMENT);) {

			pstm.setInt(1, ar);
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

		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new JDBCConnectionError(
					Messages.CONNECTION_ESTABILISHED_FAILURE);
		}

		return transaction;
	}

	@Override
	public ArrayList<Integer> getAllAccounts(int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_ACCOUNTS);) {

			pt.setInt(1, userId);

			ResultSet resSet = pt.executeQuery();
			ArrayList<Integer> accList = new ArrayList<Integer>();
			while (resSet.next()) {
				accList.add(resSet.getInt(1));
			}
			return accList;
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isFirstTimeUser(int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.IS_NEW_USER);) {

			pt.setInt(1, userId);

			ResultSet resSet = pt.executeQuery();

			if (resSet.next()) {
				if (resSet.getString(1)==null)
					return true;
				return false;
			} else {
				return true;
			}
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void completeProfile(ArrayList<String> userData, int userId) throws CompleteProfileException{
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.COMPLETE_USER_PROFILE);) {

			pt.setString(1, userData.get(1));
			pt.setString(2, userData.get(2));
			pt.setString(3, userData.get(3));
			pt.setInt(4, userId);
			
			pt.executeUpdate();
		} catch (JDBCConnectionError e) {
			throw new CompleteProfileException(Messages.UPDATE_FAILED);
		} catch (SQLException e) {
			throw new CompleteProfileException(Messages.UPDATE_FAILED);
		}
	}

}
