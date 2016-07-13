package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;


public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{

	public void insertBookNoPub(Book book) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book (title) values (?)", new Object[] {book.getTitle()});
	}

	public Integer saveBookWithID(Book book) throws ClassNotFoundException, SQLException{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				if(book.getPublisher() != null) {
					statement = con.prepareStatement("insert into tbl_book (title, pubId) values (?,?)", new String[] {"id"} );
					statement.setInt(2, book.getPublisher().getPublisherId());
				}
				else {
					statement = con.prepareStatement("insert into tbl_book (title) values (?)", new String[] {"id"} );
				}
				statement.setString(1, book.getTitle());
				return statement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}


	public void insertBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book (title, pubId) values (?,?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book where bookId=?", new Object[] {book.getBookId()});
	}

	public boolean searchBookId (int id) throws ClassNotFoundException, SQLException	{
		List<Book> books = template.query("select * from tbl_book where bookId = ?", new Object[] { id }, this);
		if( books.size() == 0 )
			return false;
		else
			return true;
	}

	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.queryForObject("select count(*) as count from tbl_book", Integer.class);
	}

	public List<Book> readAll(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return template.query("select * from tbl_book", this);
	}

	public List<Book> readBooksByAuthor(int pageNo, int authorId) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		List<Book> books = template.query("select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)", new Object[]{authorId}, this);
		if( books.isEmpty() ){
			Book b = new Book();
			b.setTitle("N/A");
			books.add(b);
		}
		return books;
	}

	public List<Book> readBooksByBranch(int pageNo, int branchId) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		List<Book> books = template.query("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId}, this);
		if( books.isEmpty() ){
			Book b = new Book();
			b.setTitle("N/A");
			books.add(b);
		}
		return books;
	}

	public List<Book> readBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_book where bookId not in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId}, this);
	}

	public Book readOne(Book book) throws ClassNotFoundException, SQLException {
		List<Book> books = template.query("select * from tbl_book where bookId =?", new Object[] {book.getBookId()}, this);
		for(Book b: books){
			return b;
		}
		return null;
	}

	public List<Book> readBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		if( searchString.equals("") )
			searchString = "%%";
		else
			searchString = "%" + searchString + "%";
		String query = "select * from tbl_book where title like ?";
		if(pageNo > 0){
			int index = (pageNo-1)*10;
			query += " LIMIT "+index+" , "+getPageSize();
		}
		return template.query(query, new Object[] {searchString}, this);
	}

	public List<Book> viewAvailableBooks(Integer branchId) throws ClassNotFoundException, SQLException {
		List<Book> books = template.query("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ? and noOfCopies > 0)", new Object[]{branchId}, this);
		return books;
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();

		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(p);
			books.add(b);
		}
		return books;
	}	
}
