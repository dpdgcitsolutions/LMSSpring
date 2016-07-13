package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;


public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{
	
	public void insertBookLoans(BookLoans bl) throws ClassNotFoundException, SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar dateOut = Calendar.getInstance();
		Calendar dueDate = Calendar.getInstance();
		dateOut.setTime(new Date()); // Now use today date.
		dueDate.setTime(new Date());
		dueDate.add(Calendar.DATE, 7); // Adding 7 days
		String dateOutStr = sdf.format(dateOut.getTime());
		String dueDateStr = sdf.format(dueDate.getTime());
		template.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?,?,?,?,?)", new Object[] {bl.getBookId(), bl.getBranchId(), bl.getCardNo(), dateOutStr, dueDateStr});
	}
	
//	public void deleteBookLoans(BookLoans bl) throws ClassNotFoundException, SQLException{
//		save("delete from tbl_book_loans where authorId=?", new Object[] {author.getAuthorId()});
//	}
	
//	public void deleteAll() throws ClassNotFoundException, SQLException{
//		save("delete * from tbl_author", null);
//	}
	
	public void updateDueDate(BookLoans bl) throws ClassNotFoundException, SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar dueDate = Calendar.getInstance();
		dueDate.setTime(new Date()); // Now use today date.
		dueDate.add(Calendar.DATE, 7); // Adding 7 days
		String dueDateStr = sdf.format(dueDate.getTime());
		template.update("update tbl_book_loans set dueDate = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?", new Object[] {dueDateStr, bl.getBookId(), bl.getBranchId(), bl.getCardNo(), bl.getDateOut()});
	}
	
	public void updateDateIn(BookLoans bl) throws ClassNotFoundException, SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar dateIn = Calendar.getInstance();
		dateIn.setTime(new Date()); // Now use today date.
		String dateInStr = sdf.format(dateIn.getTime());
		template.update("update tbl_book_loans set dateIn = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?", new Object[] {dateInStr, bl.getBookId(), bl.getBranchId(), bl.getCardNo(), bl.getDateOut()});
	}
	
	public List<BookLoans> readAllByBranch(int branchId) throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_book_loans where branchId = ? and dateIn is null", new Object[]{branchId}, this);
	}
	
//	public List<Author> readAll(int pageNo) throws ClassNotFoundException, SQLException{
//		setPageNo(pageNo);
//		return read("select * from tbl_book_loans", null);
//	}
//	
//	public Author readOne(Author author) throws ClassNotFoundException, SQLException{
//		List<Author> authors = read("select * from tbl_book_loans where authorId =?", new Object[] {author.getAuthorId()});
//		for(Author a: authors){
//			return a;
//		}
//		return null;
//	}
//	
//	public Integer getCount() throws ClassNotFoundException, SQLException{
//		return readCount("select count(*) as count from tbl_book_loans", null);
//	}


	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bls = new ArrayList<BookLoans>();
		while(rs.next()){
			BookLoans bl = new BookLoans();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getString("dateOut"));
			bl.setDateIn(rs.getString("dateIn"));
			bl.setDueDate(rs.getString("dueDate"));
			bls.add(bl);
		}
		return bls;
	}

}
