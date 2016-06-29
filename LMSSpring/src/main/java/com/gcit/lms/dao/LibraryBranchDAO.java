package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class LibraryBranchDAO extends BaseDAO {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void insertLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		save("insert into tbl_library_branch (branchName, branchAddress) values (?,?)", new Object[] {lib.getBranchName(), lib.getBranchAddress()});
	}
	
	public void deleteLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		save("delete from tbl_library_branch where branchId = ?", new Object[] {lib.getBranchId()});
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException{
		save("delete * from tbl_library_branch", null);
	}
	
	public void updateLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		save("update tbl_library_branch set branchName = ? , branchAddress = ? where branchId = ?", new Object[] {lib.getBranchName(), lib.getBranchAddress(), lib.getBranchId()});
	}
	
	public List<LibraryBranch> readAll() throws ClassNotFoundException, SQLException{
		return read("select * from tbl_library_branch", null);
	}
	
	public LibraryBranch readOne(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		List<LibraryBranch> libs = read("select * from tbl_library_branch where branchId =?", new Object[] {lib.getBranchId()});
		for(LibraryBranch l : libs){
			return l;
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
		BookDAO bdao = new BookDAO(connection);
		BookCopiesDAO bcdao = new BookCopiesDAO(connection);
		while(rs.next()){
			LibraryBranch l = new LibraryBranch();
			l.setBranchId(rs.getInt("branchId"));
			l.setBranchName(rs.getString("branchName"));
			l.setBranchAddress(rs.getString("branchAddress"));
			try {
				List<BookCopies> bcs = bcdao.readFirstLevel("select * from tbl_book_copies where branchId = ?", new Object[] {l.getBranchId()});
				List<Book> books = bdao.read("select * from tbl_book where bookId IN (select bookId from tbl_book_copies where branchId = ?)", new Object[]{l.getBranchId()});
				if( books.isEmpty() ){
					Book b = new Book();
					b.setTitle("N/A");
					Author a = new Author();
					a.setAuthorName("N/A");
					List<Author> as = new ArrayList<Author>();
					as.add(a);
					b.setAuthors(as);
					List<Genre> gs = new ArrayList<Genre>();
					Genre g = new Genre();
					g.setGenre_name("N/A");
					gs.add(g);
					Publisher p = new Publisher();
					p.setPublisherName("N/A");
								
					books.add(b);
				}
//				if( bcs.isEmpty() )
//				{
//					BookCopies bc = new BookCopies();
//					bc.setBookId(0);
//					bc.setBranchId(0);
//					bc.setNoOfCopies(0);
//				}
				l.setBookCopies(bcs);
				l.setBooks(books);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			libs.add(l);
		}
		return libs;
	}

	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws SQLException {
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
