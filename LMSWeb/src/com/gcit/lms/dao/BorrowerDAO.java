package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void insertBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		save("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", new Object[] {b.getName(), b.getAddress(), b.getPhone()});
	}
	
	public void updateBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		save("update tbl_borrower set name = ?, address = ?, phone = ?, where cardNo = ?", new Object[] {b.getName(), b.getAddress(), b.getPhone(), b.getCardNo()});
	}
	
	public void deleteBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		save("delete from tbl_borrower where cardNo = ?", new Object[] {b.getCardNo()});
	}
	
	public List<Borrower> readAll() throws ClassNotFoundException, SQLException{
		return read("select * from tbl_borrower", null);
	}
	
	public List<Borrower> readFromBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		return read("select * from tbl_borrower where cardNo in (select cardNo from tbl_book_loans where branchId = ?)", new Object[] {lib.getBranchId()});
	}
	
	public Borrower readOne(Borrower b) throws ClassNotFoundException, SQLException{
		List<Borrower> bs = read("select * from tbl_borrower where cardNo = ?", new Object[] {b.getCardNo()});
		for( Borrower bo : bs )
			return bo;
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> bs = new ArrayList<Borrower>();
		BookDAO bdao = new BookDAO(connection);
		BookLoansDAO bldao = new BookLoansDAO(connection);
		while(rs.next()){
			Borrower bo = new Borrower();
			bo.setCardNo(rs.getInt("cardNo"));
			bo.setName(rs.getString("name"));
			bo.setAddress(rs.getString("address"));
			bo.setPhone(rs.getString("phone"));
			try{
				List<Book> books = bdao.read("select * from tbl_book where bookId in (select bookId from tbl_book_loans where cardNo = ?)", new Object[]{bo.getCardNo()});
				if( books.isEmpty() ){
					Book b = new Book();
					b.setTitle("N/A");
					books.add(b);
				}
				List<BookLoans> bookloans = bldao.read("select * from tbl_book_loans where cardNo = ? and dateIn is null", new Object[] {bo.getCardNo()});
				bo.setBooks(books);
				bo.setBookLoans(bookloans);
			} catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			bs.add(bo);
		}
		return bs;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Borrower> bs = new ArrayList<Borrower>();
		while(rs.next()){
			Borrower bo = new Borrower();
			bo.setCardNo(rs.getInt("cardNo"));
			bo.setName(rs.getString("name"));
			bo.setAddress(rs.getString("address"));
			bo.setPhone(rs.getString("phone"));
			bs.add(bo);
		}
		return bs;
	}
}
