package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO{
	
	public PublisherDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void insertPublisher(Publisher p) throws ClassNotFoundException, SQLException{
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {p.getPublisherName(), p.getPublisherAddress(), p.getPublisherPhone()});
	}

	public List<Publisher> readAll() throws ClassNotFoundException, SQLException{
		return read("select * from tbl_publisher", null);
	}

	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			try {
				BookDAO bdao = new BookDAO(connection);
				List<Book> books = bdao.readFirstLevel("select * from tbl_book where pubId = ?", new Object[]{p.getPublisherId()});
				if( books.isEmpty() ){
					Book b = new Book();
					b.setTitle("N/A");
					books.add(b);
				}
				p.setBooks(books);;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			publishers.add(p);
		}
		return publishers;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			publishers.add(p);
		}
		return publishers;
	}

}
