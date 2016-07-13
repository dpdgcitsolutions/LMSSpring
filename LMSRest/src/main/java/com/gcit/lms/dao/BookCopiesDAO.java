package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>>{

	public void insertBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?,?,?)", new Object[] {bc.getBookId(), bc.getBranchId(), bc.getNoOfCopies()});
	}
	
	public void updateBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		template.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", new Object[] {bc.getNoOfCopies(), bc.getBookId(), bc.getBranchId()});
	}
	
	public void deleteBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_copies where bookId=? and branchId=?", new Object[] {bc.getBookId(), bc.getBranchId()});
	}
	
	public BookCopies readOne(BookCopies bc) throws ClassNotFoundException, SQLException {
		List<BookCopies> bcs = template.query("select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {bc.getBookId(), bc.getBranchId()}, this);
		for(BookCopies b: bcs){
			return b;
		}
		return null;
	}
	
	public List<BookCopies> readAllByBranch(int branchId) throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_book_copies where branchId = ?", new Object[]{branchId}, this);
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
