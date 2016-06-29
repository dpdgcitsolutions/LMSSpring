package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class BookAuthorsDAO extends BaseDAO {

	public BookAuthorsDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void insertBookAuthors(Book book) throws ClassNotFoundException, SQLException{
		for(Author a: book.getAuthors()) {
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)", new Object[] {book.getBookId(), a.getAuthorId()});
		}
	}
	
	public void deleteBookAuthors(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()});
	}
//	public void updateBookAuthors(Book book, int authorId) throws ClassNotFoundException, SQLException{
//		for( Author a : book.getAuthors() )
//			save("update tbl_book_authors set authorId = ? where bookId = ? and authorId = ?", new Object[] {a.getAuthorId(), book.getBookId(), authorId});
//	}
//	
//	public void updateAuthor(BookAuthors bkau) throws ClassNotFoundException, SQLException
//	{
//		save("update tbl_book_authors set authorId = ? where bookId = ?", new Object[] {bkau.getAuthorId(), bkau.getBookId()} );
//	}
//	
//	public void updateBook(BookAuthors bkau) throws ClassNotFoundException, SQLException
//	{
//		save("update tbl_book_authors set bookId = ? where authorId = ?", new Object[] {bkau.getBookId(), bkau.getAuthorId()} );
//	}
	
//	public static void insert(AuthorDAO authorDAO, BookDAO bookDAO, BookAuthorsDAO bkauDAO) throws ClassNotFoundException, SQLException
//	{
//		Scanner scan = new Scanner(System.in);
//		System.out.print("Enter author's ID: ");
//		int authorId = scan.nextInt();
//		if( authorDAO.searchAuthorId(authorId) == false )
//		{
//			System.out.println("Can't find author's ID");
//			return;
//		}
//		System.out.print("Enter book's ID: ");
//		int bookId = scan.nextInt();
//		if( bookDAO.searchBookId(bookId) == false )
//		{
//			System.out.println("Can't find book's ID");
//			return;
//		}
//		BookAuthors bkau = new BookAuthors();
//		bkau.setAuthorId(authorId);
//		bkau.setBookId(bookId);
//		bkauDAO.insertBookAuthors(bkau);
//	}
	
//	public void deleteBookAuthors(BookAuthors bkau) throws ClassNotFoundException, SQLException{
//		save("delete from tbl_book_authors where where bookId = ?, authorId = ?", new Object[] {bkau.getBookId(), bkau.getAuthorId()});
//	}
//	@Override
//	public List<BookAuthors> extractData(ResultSet rs) throws SQLException {
//		List<BookAuthors> bkau = new ArrayList<BookAuthors>();
//		//BookDAO bdao = new BookDAO(connection);
//		while(rs.next()){
//			BookAuthors b = new BookAuthors();
//			b.setAuthorId(rs.getInt("authorId"));
//			b.setBookId(rs.getInt("bookId"));
////			try {
////				a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN(select bookId from tbl_book_authors where authorId = ?", new Object[]{a.getAuthorId()}));
////			} catch (ClassNotFoundException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//			bkau.add(b);
//		}
//		return bkau;
//	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
