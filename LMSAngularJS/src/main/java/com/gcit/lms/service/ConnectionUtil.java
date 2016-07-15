package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static String driver= "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/library";
	private static String username = "root";
	private static String pass = "Katyroot";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, pass);
		conn.setAutoCommit(false);
		return conn;
	}
}
