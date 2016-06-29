package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerService {
	ConnectionUtil util = new ConnectionUtil();
	
	public Borrower checkCardNo(Borrower bo) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			return bodao.readOne(bo);
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
	
	public Borrower viewBorrowerById(Borrower b) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BorrowerDAO bodao = new BorrowerDAO(conn);
			return bodao.readOne(b);
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
	
	public List<Book> viewAvailableBooks(Integer branchId) throws ClassNotFoundException, SQLException{
		Connection conn = util.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.viewAvailableBooks(branchId);
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

	public void checkOut(int bookId, int branchId, int cardNo, BookCopies bc) throws ClassNotFoundException, SQLException {
		Connection conn = util.getConnection();
		try{
			BookLoansDAO bldao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			BookLoans bl = new BookLoans();
			bl.setBookId(bookId);
			bl.setBranchId(branchId);
			bl.setCardNo(cardNo);
			bldao.insertBookLoans(bl);
			bcdao.updateBookCopies(bc);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void checkIn(int bookId, int branchId, int cardNo, String dateOut, BookCopies bc) throws ClassNotFoundException, SQLException {
		Connection conn = util.getConnection();
		try{
			BookLoansDAO bldao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			BookLoans bl = new BookLoans();
			bl.setBookId(bookId);
			bl.setBranchId(branchId);
			bl.setCardNo(cardNo);
			bl.setDateOut(dateOut);
			System.out.println("date in");
			bldao.updateDateIn(bl);
			System.out.println("done date in");
			bcdao.updateBookCopies(bc);
			System.out.println("book copies");
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
	}
}
