package com.cg.obs.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.dao.CustomerDaoImpl;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;

public class CustomerDaoImplTest {

	static Customer dummyCustomer;
	static Transactions dummyTran;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();

		// for Change in Address
		PreparedStatement setAddPt = conn.prepareStatement("UPDATE CUSTOMER SET ADDRESS=? WHERE USER_ID=?");
		PreparedStatement selMinPt = conn.prepareStatement("INSERT INTO TRANSACTIONS VALUES(?,?,?,?,?,?)");
		
		dummyCustomer = (new CustomerDaoImpl()).getCustomerDetails(120);
		dummyTran = new Transactions(12346,"SUCCESSFUL",Date.valueOf(LocalDate.now()),"c",5000,1001);
		
		setAddPt.setString(1, "Whitefield, Bangalore");
		setAddPt.setLong(2, 120);
		
		selMinPt.setLong(1, dummyTran.getTransactionId());
		selMinPt.setString(2, dummyTran.getTransactionDesc());
		selMinPt.setDate(3, dummyTran.getDateOfTransaction());
		selMinPt.setString(4, dummyTran.getTransactionType());
		selMinPt.setDouble(5, dummyTran.getTransactionAmount());
		selMinPt.setLong(6, dummyTran.getAccountId());
		
		setAddPt.executeUpdate();
		selMinPt.executeUpdate();
		
		// For Adding dummy transaction to the account
		
		// if connection is not closed, then close it
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
		List<Transactions> tranList;
		try {
			tranList = (new CustomerDaoImpl()).getMiniStatement(1001);
			assertNotNull(tranList);
			//checking if same object retrieved, tran.get(0) gets the latest transaction 
			assertEquals(dummyTran.toString(), tranList.get(tranList.size()-1).toString());
		} catch (OnlineBankingException e) {
			fail(e.getMessage());
		}
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection conn = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement pt = conn.prepareStatement("UPDATE CUSTOMER SET ADDRESS=? WHERE USER_ID=?");
		PreparedStatement delTran = conn.prepareStatement("DELETE FROM TRANSACTIONS WHERE TRANSACTION_ID=?");
		
		//rolling back changes 
		pt.setString(1, dummyCustomer.getAddress());
		pt.setLong(2, 120);
		pt.executeUpdate();
		
		delTran.setLong(1, 12346);
		delTran.executeUpdate();
		
		//closing resources
		if (conn != null && !conn.isClosed())
			conn.close();
	}

}
