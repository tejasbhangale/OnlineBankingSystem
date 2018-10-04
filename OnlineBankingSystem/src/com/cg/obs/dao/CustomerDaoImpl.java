package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.PasswordUpdateException;
import com.cg.obs.util.ConnectionProvider;

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
				customer.setAccountId(id);
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
			pt.setInt(3, customer.getAccountId());

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
	public boolean checkOldPass(String oldPass, int id) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.CHECK_OLD_PASSWORD);) {
			pt.setInt(1, id);

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
	public void updatePassword(String newPass, int id)
			throws PasswordUpdateException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_PASSWORD);) {

			String[] pass = newPass.split(" ");
			pt.setString(1, pass[0]);
			pt.setInt(2, id);
			pt.executeUpdate();
		} catch (JDBCConnectionError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int requestChequeBook(int id) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GENERATE_SERVICE_REQUEST);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_SERVICE_REQUEST_NUMBER);) {

			Date date = Date.valueOf(LocalDate.now());
			pt.setString(1, "New ChequeBook Request");
			pt.setInt(2, id);
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
	public ServiceTracker getRequestStatus(int reqNum, int accNum) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_REQUEST_STATUS);) {

			pt.setInt(1, reqNum);
			pt.setInt(2, accNum);
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
			while (resSet.next()) {
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

}
