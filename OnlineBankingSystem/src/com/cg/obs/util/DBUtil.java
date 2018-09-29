package com.cg.obs.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	

	public static Connection getConnection(){
		
		
		Connection conn = null;
		Properties p =new Properties();
		
		try {
			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			FileInputStream f = new FileInputStream("res//db.properties");
			p.load(f);
			
			String url = p.getProperty("db.url");
			String user_name = p.getProperty("db.username");
			String password = p.getProperty("db.password");
			
			conn = DriverManager.getConnection(url,user_name,password);
			
			
			
			
		} catch (SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return conn;
		
	}
	
	
	
	
	
	
}
