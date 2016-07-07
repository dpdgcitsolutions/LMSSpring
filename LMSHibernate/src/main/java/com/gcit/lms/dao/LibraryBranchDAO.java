package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>>{

	public void insertLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_library_branch (branchName, branchAddress) values (?,?)", new Object[] {lib.getBranchName(), lib.getBranchAddress()});
	}
	
	public void deleteLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_library_branch where branchId = ?", new Object[] {lib.getBranchId()});
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException{
		template.update("delete * from tbl_library_branch");
	}
	
	public void updateLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		template.update("update tbl_library_branch set branchName = ? , branchAddress = ? where branchId = ?", new Object[] {lib.getBranchName(), lib.getBranchAddress(), lib.getBranchId()});
	}
	
	public List<LibraryBranch> readAll() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_library_branch", this);
	}
	
	public LibraryBranch readOne(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		List<LibraryBranch> libs = template.query("select * from tbl_library_branch where branchId =?", new Object[] {lib.getBranchId()}, this);
		for(LibraryBranch l : libs){
			return l;
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
		while(rs.next()){
			LibraryBranch l = new LibraryBranch();
			l.setBranchId(rs.getInt("branchId"));
			l.setBranchName(rs.getString("branchName"));
			l.setBranchAddress(rs.getString("branchAddress"));
			libs.add(l);
		}
		return libs;
	}
}
