package com.cg.obs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
					"system", "orcl11g");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
