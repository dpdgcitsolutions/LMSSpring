package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC {
	
	private static String driver= "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/library";
	private static String username = "root";
	private static String pass = "Kat6825537674";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, pass);
		String query = "select * from tbl_author";
		//Statement stmt = conn.createStatement();
		query = "select * from tbl_author where authorName like ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a new Author: ");
		String authorName = scan.nextLine();
		
		query = "insert into tbl_author (authorName) values('"+authorName+"')";
		query = "select * from tbl_author where authorName like '"+authorName+"'";
		query = "select * from tbl_author where authorId = ?";
		//stmt.executeUpdate(query);
		
		//pstmt.setString(1, authorName);
		pstmt.setInt(1, 200);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			System.out.println("Author Name: " +rs.getString("authorName"));
			System.out.println("Author ID: "+rs.getInt("authorId"));
			System.out.println("-----------------------");
		}

	}
}
