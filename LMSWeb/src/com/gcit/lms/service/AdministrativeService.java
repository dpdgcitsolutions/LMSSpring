package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorsDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenresDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class AdministrativeService {
	
	ConnectionUtil util = new ConnectionUtil();
	
	public void createAuthor(Author author) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			adao.insertAuthor(author);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void createBorrower(Borrower bo) throws SQLException, ClassNotFoundException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			bodao.insertBorrower(bo);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void editBorrower(Borrower bo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			bodao.updateBorrower(bo);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void editAuthor(Author author) throws ClassNotFoundException, SQLException
	{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(author);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void editBook(Book b) throws ClassNotFoundException, SQLException {
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(b);
			BookAuthorsDAO badao = new BookAuthorsDAO(conn);
			badao.deleteBookAuthors(b);
			badao.insertBookAuthors(b);
			BookGenresDAO bgdao = new BookGenresDAO(conn);
			bgdao.deleteBookGenres(b);
			bgdao.insertBookGenres(b);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void createGenre(Genre g) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			GenreDAO gdao = new GenreDAO(conn);
			gdao.insertGenre(g);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void createPublisher(Publisher p) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.insertPublisher(p);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void editBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException
	{
		Connection conn = util.getConnection();
		try{
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);		
			bcdao.updateBookCopies(bc);			
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public void createBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException
	{
		Connection conn = util.getConnection();
		try{
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);		
			bcdao.insertBookCopies(bc);			
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	

	public void editBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
			ldao.updateLibraryBranch(lib);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	
	public void createLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException
	{
		Connection conn = util.getConnection();
		try{
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.insertLibraryBranch(lib);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	
	public void createBook(Book book) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.saveBookWithID(book);
			book.setBookId(bookId);
			
			BookAuthorsDAO bkaudao = new BookAuthorsDAO(conn);
			bkaudao.insertBookAuthors(book);
			BookGenresDAO bgdao = new BookGenresDAO(conn);
			bgdao.insertBookGenres(book);
			
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public List<LibraryBranch> viewLibraryBranch() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public BookCopies viewBookCopiesByID(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		Connection conn = util.getConnection();
		try{
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			return bcdao.readOne(bc);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	
	
	public Book viewBookByID(Integer bookId) throws ClassNotFoundException, SQLException{
		Book b = new Book();
		b.setBookId(bookId);
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readOne(b);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Book> viewBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBooksNotInBranch(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Publisher> viewPublishers() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;	
	}
	
	public List<Genre> viewGenres() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;	
	}
	
	public List<Genre> viewGenresFirstLevel() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readAllFirstLevel();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;	
	}
	
	public List<Borrower> viewBorrowers() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			return bodao.readAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Author> viewAuthorsBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readBySearchString(searchString, pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Book> viewBooksBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBySearchString(searchString, pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	
	public List<Author> viewAuthorsFirstLevel(int pageNo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllFirstLevel(pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public Integer getAuthorsCount() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.getCount();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public Integer getBooksCount() throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.getCount();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Author> viewAuthors(int pageNo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAll(pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Book> viewBooks(int pageNo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAll(pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}	
	
	public Author viewAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException{
		Author a = new Author();
		a.setAuthorId(authorID);
		Connection conn = util.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readOne(a);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public LibraryBranch viewBranchById(Integer branchID) throws ClassNotFoundException, SQLException {
		LibraryBranch l = new LibraryBranch();
		l.setBranchId(branchID);
		Connection conn = util.getConnection();
		try{
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readOne(l);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}

	

}
