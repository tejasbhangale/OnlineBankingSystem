package com.cg.obs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.service.LoginServiceImpl;
import com.cg.obs.util.ConnectionProvider;

public class LoginServiceImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();

		PreparedStatement addAdmin = conn.prepareStatement("INSERT INTO ADMIN_TABLE VALUES(?,?,?)");
		PreparedStatement addUser = conn.prepareStatement("INSERT INTO USER_TABLE(user_id, login_password, lock_status) VALUES(?,?,?)");

		addAdmin.setInt(1, 100);
		addAdmin.setString(2, "dummyadmin");
		addAdmin.setString(3, "dummypass");
		
		addUser.setLong(1, 555);
		addUser.setString(2, "dummy@pass");
		addUser.setString(3, "u");
		
		addAdmin.executeUpdate();
		addUser.executeUpdate();
		
		// if connection is not closed, then close it
		if (conn != null && !conn.isClosed())
			conn.close();
	}

	@Test
	public void testGetAdminLogin() {
		try {
			boolean dummyAdmin = (new LoginServiceImpl()).getAdminLogin("dummyadmin","dummypass");
			assertTrue(dummyAdmin);
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetUserLogin() {
		try {
			LoginServiceImpl loginObj = new LoginServiceImpl();
			long userId = loginObj.getUserLogin(555l,"dummy@pass");
			assertNotNull(userId);
			assertEquals(555, userId);
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();

		PreparedStatement delAdmin = conn.prepareStatement("DELETE FROM ADMIN_TABLE WHERE ADMIN_USER_ID='dummyadmin'");
		PreparedStatement delUser = conn.prepareStatement("DELETE FROM USER_TABLE WHERE USER_ID=?");
		
		delUser.setLong(1, 555);
		delAdmin.executeUpdate();
		delUser.executeUpdate();
		if (conn != null && !conn.isClosed())
			conn.close();
	}
}
