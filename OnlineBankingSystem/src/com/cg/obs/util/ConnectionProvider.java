package com.cg.obs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cg.obs.exception.OnlineBankingException;


public enum ConnectionProvider {

	DEFAULT_INSTANCE;

	private String driver;
	private String username;
	private String password;
	private String url;

	private Logger log = Logger.getLogger("DB");

	private ConnectionProvider() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("res/db.properties"));
			username = props.getProperty("db.username");
			driver = props.getProperty("db.driver");
			password = props.getProperty("db.password");
			url = props.getProperty("db.url");
			Class.forName(driver);
			log.info("Driver Loaded");
		} catch (ClassNotFoundException | IOException e) {
			log.error(e);
		}
	}

	public Connection getConnection() throws OnlineBankingException {
		Connection con = null;

		try {
			if (url != null && username != null && password != null) {
				con = DriverManager.getConnection(url, username, password);
			}else
				
				throw new OnlineBankingException(Messages.CONNECTION_CONFIGURATION_FAILURE);
		} catch (SQLException e) {
			log.error(e);
			throw new OnlineBankingException(Messages.CONNECTION_ESTABILISHED_FAILURE);
		}
		return con;
	}
}
