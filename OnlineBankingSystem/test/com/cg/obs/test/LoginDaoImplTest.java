package com.cg.obs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.bean.Admin;
import com.cg.obs.bean.User;
import com.cg.obs.dao.LoginDaoImpl;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;

public class LoginDaoImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();

		PreparedStatement addAdmin = conn.prepareStatement("INSERT INTO ADMIN_TABLE VALUES(?,?,?)");
		PreparedStatement addUser = conn.prepareStatement("INSERT INTO USER_TABLE(user_id, login_password) VALUES(?,?)");

		addAdmin.setInt(1, 100);
		addAdmin.setString(2, "dummyadmin");
		addAdmin.setString(3, "dummypass");
		
		addUser.setLong(1, 555);
		addUser.setString(2, "dummy@pass");
		
		addAdmin.executeUpdate();
		addUser.executeUpdate();
		
		// if connection is not closed, then close it
		if (conn != null && !conn.isClosed())
			conn.close();
	}

	@Test
	public void testGetAdminLogin() {
		try {
			Admin dummyAdmin = (new LoginDaoImpl()).getAdminLogin("dummyadmin");
			assertNotNull(dummyAdmin);
			assertEquals("dummyadmin", dummyAdmin.getAdminUserId());
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetUserLogin() {
		try {
			User dummyUser = (new LoginDaoImpl()).getUserLogin(555);
			assertNotNull(dummyUser);
			assertEquals("dummy@pass", dummyUser.getLoginPassword());
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
