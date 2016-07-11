package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorsDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenresDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class LibrarianService {
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	BookAuthorsDAO badao;
	
	@Autowired
	BookGenresDAO bgdao;
	
	@Autowired
	BookCopiesDAO bcdao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Autowired
	BookLoansDAO bldao;

	public LibraryBranch viewBranchById(Integer branchID) throws ClassNotFoundException, SQLException {
		LibraryBranch l = new LibraryBranch();
		l.setBranchId(branchID);
		return ldao.readOne(l);
	}
	
	@Transactional
	public void createBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.insertBookCopies(bc);
	}
	
	@Transactional
	public void editBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.updateBookCopies(bc);
	}
	
	@Transactional
	public void editBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		ldao.updateLibraryBranch(lib);
	}
	
	public Borrower viewBorrowerByCardNo(int cardNo) throws ClassNotFoundException, SQLException{
		Borrower b = new Borrower();
		b.setCardNo(cardNo);
		return bodao.readOne(b);
	}
	
	public List<LibraryBranch> viewLibraryBranch() throws ClassNotFoundException, SQLException{
		return ldao.readAll();
	}
	
	public List<Book> viewBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksNotInBranch(branchId);
	}
	
	public List<Book> viewBooks(int pageNo) throws ClassNotFoundException, SQLException{
		return bdao.readAll(pageNo);
	}
	
	public List<BookLoans> viewBookLoansByBranch(int branchId) throws ClassNotFoundException, SQLException{
		BookLoans bl = new BookLoans();
		bl.setBranchId(branchId);
		return bldao.readAllByBranch(branchId);
	}
	
	public List<BookCopies> viewBookCopiesByBranch(int branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBranchId(branchId);
		return bcdao.readAllByBranch(branchId);
	}
	
	public BookCopies viewBookCopiesByID(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		return bcdao.readOne(bc);
	}
	
	public Book viewBookByID(Integer bookId) throws ClassNotFoundException, SQLException{
		Book b = new Book();
		b.setBookId(bookId);
		return bdao.readOne(b);
	}
	
	public List<Borrower> viewBorrowerByBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		return bodao.readFromBranch(lib);
	}

	@Transactional
	public void extendDueDate(int bookId, int branchId, int cardNo, String dateOut) throws ClassNotFoundException, SQLException {
		BookLoans bl = new BookLoans();
		bl.setBookId(bookId);
		bl.setBranchId(branchId);
		bl.setCardNo(cardNo);
		bl.setDateOut(dateOut);
		bldao.updateDueDate(bl);
	}
	
	public List<Book> viewBooksByBranch(int pageNo, int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksByBranch(pageNo, branchId);
	}
}
