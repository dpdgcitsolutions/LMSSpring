package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class LibrarianService {
	ConnectionUtil util = new ConnectionUtil();	

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
	
	public void createBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
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
	
	public void editBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
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
	
	public Borrower viewBorrowerByCardNo(int cardNo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			Borrower b = new Borrower();
			b.setCardNo(cardNo);
			BorrowerDAO bodao = new BorrowerDAO(conn);
			return bodao.readOne(b);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
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
	
	public List<BookLoans> viewBookLoansByBranch(int branchId) throws ClassNotFoundException, SQLException{
		BookLoans bl = new BookLoans();
		bl.setBranchId(branchId);
		Connection conn = util.getConnection();
		try{
			BookLoansDAO bldao = new BookLoansDAO(conn);
			return bldao.readAllByBranch(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<BookCopies> viewBookCopiesByBranch(int branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBranchId(branchId);
		Connection conn = util.getConnection();
		try{
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			return bcdao.readAllByBranch(branchId);
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
	
	public List<Borrower> viewBorrowerByBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			return bodao.readFromBranch(lib);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}

	public void extendDueDate(int bookId, int branchId, int cardNo, String dateOut) throws ClassNotFoundException, SQLException {
		Connection conn = util.getConnection();
		try{
			BookLoansDAO bldao = new BookLoansDAO(conn);
			BookLoans bl = new BookLoans();
			bl.setBookId(bookId);
			bl.setBranchId(branchId);
			bl.setCardNo(cardNo);
			bl.setDateOut(dateOut);
			bldao.updateDueDate(bl);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
	}
}
