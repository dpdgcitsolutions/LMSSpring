package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO {

	public BookCopiesDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void insertBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?,?,?)", new Object[] {bc.getBookId(), bc.getBranchId(), bc.getNoOfCopies()});
	}
	
	public void updateBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", new Object[] {bc.getNoOfCopies(), bc.getBookId(), bc.getBranchId()});
	}
	
	public void deleteBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book_copies where bookId=? and branchId=?", new Object[] {bc.getBookId(), bc.getBranchId()});
	}
	
	public BookCopies readOne(BookCopies bc) throws ClassNotFoundException, SQLException {
		List<BookCopies> bcs = read("select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {bc.getBookId(), bc.getBranchId()});
		for(BookCopies b: bcs){
			return b;
		}
		return null;
	}
	
	public List<BookCopies> readAllByBranch(int branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ?", new Object[]{branchId});
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<BookCopies> bcs = new ArrayList<BookCopies>();
		while(rs.next()){
			BookCopies b = new BookCopies();
			b.setBookId(rs.getInt("bookId"));
			b.setBranchId(rs.getInt("branchId"));
			b.setNoOfCopies(rs.getInt("noOfCopies"));
			bcs.add(b);
		}
		return bcs;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bcs = new ArrayList<BookCopies>();
		while(rs.next()){
			BookCopies b = new BookCopies();
			b.setBookId(rs.getInt("bookId"));
			b.setBranchId(rs.getInt("branchId"));
			b.setNoOfCopies(rs.getInt("noOfCopies"));
			bcs.add(b);
		}
		return bcs;
	}

	
}
