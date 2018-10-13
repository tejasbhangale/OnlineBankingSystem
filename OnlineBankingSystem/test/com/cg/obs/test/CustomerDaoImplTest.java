package com.cg.obs.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.bean.Customer;
import com.cg.obs.dao.CustomerDaoImpl;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;

public class CustomerDaoImplTest {
	
	static Customer dummyCustomer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement pt = conn.prepareStatement("UPDATE CUSTOMER SET ADDRESS=? WHERE USER_ID=?");
		
		dummyCustomer = (new CustomerDaoImpl()).getCustomerDetails(120);
		
		pt.setString(1, "Whitefield, Bangalore");
		pt.setLong(2, 120);

		pt.executeUpdate();

		if (conn != null && !conn.isClosed())
			conn.close();
	}

	@Test
	public void testUpdateCustomerDetails() {
		try {
			Customer getCus = (new CustomerDaoImpl()).getCustomerDetails(120);
			assertNotNull(getCus);
			assertEquals("Whitefield, Bangalore", getCus.getAddress());
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetMiniStatement() {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement pt = conn.prepareStatement("UPDATE CUSTOMER SET ADDRESS=? WHERE USER_ID=?");
		pt.setString(1, dummyCustomer.getAddress());
		pt.setLong(2, 120);

		pt.executeUpdate();
		if (conn != null && !conn.isClosed())
			conn.close();
	}

}
