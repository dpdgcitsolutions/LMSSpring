package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{

	public void insertBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		System.out.println("2");
		template.update("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", new Object[] {b.getName(), b.getAddress(), b.getPhone()});
	}
	
	public void updateBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		template.update("update tbl_borrower set name = ?, address = ?, phone = ?, where cardNo = ?", new Object[] {b.getName(), b.getAddress(), b.getPhone(), b.getCardNo()});
	}
	
	public void deleteBorrower(Borrower b) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {b.getCardNo()});
	}
	
	public List<Borrower> readAll() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_borrower", this);
	}
	
	public List<Borrower> readFromBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_borrower where cardNo in (select cardNo from tbl_book_loans where branchId = ?)", new Object[] {lib.getBranchId()}, this);
	}
	
	public Borrower readOne(Borrower b) throws ClassNotFoundException, SQLException{
		List<Borrower> bs = template.query("select * from tbl_borrower where cardNo = ?", new Object[] {b.getCardNo()}, this);
		for( Borrower bo : bs )
			return bo;
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
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
