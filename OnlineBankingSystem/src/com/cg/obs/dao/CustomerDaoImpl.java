package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.obs.bean.Customer;
import com.cg.obs.exception.OnlineBankingException;
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

		} catch (OnlineBankingException e) {
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

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

}
