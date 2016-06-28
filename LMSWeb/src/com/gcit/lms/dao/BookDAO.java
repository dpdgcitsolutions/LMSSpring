package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class BookDAO extends BaseDAO {
	public BookDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void insertBookNoPub(Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book (title) values (?)", new Object[] {book.getTitle()});
	}
	
	public Integer saveBookWithID(Book book) throws ClassNotFoundException, SQLException
	{
		return saveWithID("insert into tbl_book (title, pubId) values (?,?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public void insertBook(Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book (title, pubId) values (?,?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		save("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book where bookId=?", new Object[] {book.getBookId()});
	}
	
	public boolean searchBookId (int id) throws ClassNotFoundException, SQLException	{
		List<Book> books = read("select * from tbl_book where bookId = ?", new Object[] { id });
		if( books.size() == 0 )
			return false;
		else
			return true;
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as count from tbl_book", null);
	}

	public List<Book> readAll(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("select * from tbl_book", null);
	}
	
	public List<Book> readBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException {
		return readFirstLevel("select * from tbl_book where bookId not in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId});
	}
	
	public Book readOne(Book book) throws ClassNotFoundException, SQLException {
		List<Book> books = read("select * from tbl_book where bookId =?", new Object[] {book.getBookId()});
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
		return read("select * from tbl_book where title like ?", new Object[] {searchString});
	}
	
	public List<Book> viewAvailableBooks(Integer branchId) throws ClassNotFoundException, SQLException {
		List<Book> books = read("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ? and noOfCopies > 0)", new Object[]{branchId});
		return books;
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		PublisherDAO pdao = new PublisherDAO(connection);
		AuthorDAO adao = new AuthorDAO(connection);
		GenreDAO gdao = new GenreDAO(connection);
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			//read Publisher
			try {
				List<Publisher> pubs = pdao.read("select * from tbl_publisher where publisherId = ?", new Object[]{rs.getInt("pubId")});
				if( !pubs.isEmpty() )
				{
					b.setPublisher(pubs.get(0));
				}
				else
				{
					Publisher pub = new Publisher();
					pub.setPublisherName("N/A");
					b.setPublisher(pub);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// read Author
			try {
				b.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId IN(select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// read Genre
			try {
				b.setGenres(gdao.readFirstLevel("select * from tbl_genre where genre_id IN(select genre_id from tbl_book_genres where bookId = ?)", new Object[]{b.getBookId()}));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

	

	
}
