package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public abstract class BaseDAO {
	
	private int pageNo = -1;
	
	private int pageSize = 10;
	
	public Connection connection;
	
	public BaseDAO(Connection conn){
		this.connection = conn;
	}
	
	public void save(String query, Object[] vals) throws ClassNotFoundException, SQLException{
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		if(vals !=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count ++;
			}
		}
		pstmt.executeUpdate();
	}
	
	public Integer saveWithID(String query, Object[] vals) throws ClassNotFoundException, SQLException{
		
		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		if(vals !=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count ++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs!=null){
			rs.first();
			return rs.getInt(1);
		}else{
			return -1;
		}
	}
	
	
	public <T> List<T> read(String query, Object[] vals) throws SQLException, ClassNotFoundException{
		int pageNo = getPageNo();
		if(pageNo > 0){
			int index = (pageNo-1)*10;
			query += " LIMIT "+index+" , "+getPageSize();
		}
		PreparedStatement pstmt = connection.prepareStatement(query);
		if(vals !=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count ++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		
		return (List<T>) extractData(rs);
	}

	public abstract List<?> extractData(ResultSet rs) throws SQLException;
	
	public <T> List<T> readFirstLevel(String query, Object[] vals) throws SQLException, ClassNotFoundException{
		PreparedStatement pstmt = connection.prepareStatement(query);
		if(vals !=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count ++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		
		return (List<T>) extractDataFirstLevel(rs);
	}

	public abstract List<?> extractDataFirstLevel(ResultSet rs) throws SQLException;

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer readCount(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = connection.prepareStatement(query);
		if(vals !=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count ++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			return rs.getInt("count");
		}
		return null;
	}
}
