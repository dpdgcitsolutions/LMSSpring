package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{
	
	public void insertPublisher(Publisher p) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {p.getPublisherName(), p.getPublisherAddress(), p.getPublisherPhone()});
	}

	public List<Publisher> readAll() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_publisher", this);
	}
	
	public List<Publisher> readPublisherByBook(int bookId) throws ClassNotFoundException, SQLException{
		List<Publisher> pubs = template.query("select * from tbl_publisher where publisherId in (select pubId from tbl_book where bookId = ?)", new Object[]{bookId}, this);
		if( pubs.isEmpty() ){
			Publisher p = new Publisher();
			p.setPublisherName("N/A");
			pubs.add(p);
		}
		return pubs;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			
			
			publishers.add(p);
		}
		return publishers;
	}

}
