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

		// for Change in Address
		PreparedStatement addAdmin = conn.prepareStatement("INSERT INTO ADMIN_TABLE VALUES(?,?,?)");
		PreparedStatement selMinPt = conn.prepareStatement("INSERT INTO USER_TABLE VALUES(?,?,?,?,?,?)");
		
		//dummyUser = new User(12346,"SUCCESSFUL",Date.valueOf(LocalDate.now()),"c",5000,1001);
		
		addAdmin.setInt(1, 100);
		addAdmin.setString(2, "dummyadmin");
		addAdmin.setString(3, "dummypass");
		
		/*selMinPt.setLong(1, dummyTran.getTransactionId());
		selMinPt.setString(2, dummyTran.getTransactionDesc());
		selMinPt.setDate(3, dummyTran.getDateOfTransaction());
		selMinPt.setString(4, dummyTran.getTransactionType());
		selMinPt.setDouble(5, dummyTran.getTransactionAmount());
		selMinPt.setLong(6, dummyTran.getAccountId());
		*/
		
		addAdmin.executeUpdate();
//		selMinPt.executeUpdate();
		
		// For Adding dummy transaction to the account
		
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
			//User ckUser = new User(555, loginPassword, secretQuestion, secretAnswer, transactionPassword, lockStatus)
			assertNotNull(dummyUser);
			assertEquals("dummy@user", dummyUser);
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();

		PreparedStatement delAdmin = conn.prepareStatement("DELETE FROM ADMIN_TABLE WHERE ADMIN_USER_ID='dummyadmin'");
		PreparedStatement selMinPt = conn.prepareStatement("INSERT INTO USER_TABLE VALUES(?,?,?,?,?,?)");
		
		delAdmin.executeUpdate();
		if (conn != null && !conn.isClosed())
			conn.close();
	}

}
