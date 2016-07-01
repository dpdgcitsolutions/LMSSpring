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
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerService {
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
	
	public Borrower checkCardNo(Borrower bo) throws ClassNotFoundException, SQLException{
		return bodao.readOne(bo);
	}
	
	public List<LibraryBranch> viewLibraryBranch() throws ClassNotFoundException, SQLException{
		return ldao.readAll();
	}
	
	public Borrower viewBorrowerById(Borrower b) throws ClassNotFoundException, SQLException{
		return bodao.readOne(b);
	}
	
	public BookCopies viewBookCopiesByID(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		return bcdao.readOne(bc);
	}
	
	public LibraryBranch viewBranchById(Integer branchID) throws ClassNotFoundException, SQLException {
		LibraryBranch l = new LibraryBranch();
		l.setBranchId(branchID);
		return ldao.readOne(l);
	}
	
	public List<Book> viewAvailableBooks(Integer branchId) throws ClassNotFoundException, SQLException{
		return bdao.viewAvailableBooks(branchId);
	}
	
	public Book viewBookByID(Integer bookId) throws ClassNotFoundException, SQLException{
		Book b = new Book();
		b.setBookId(bookId);
		return bdao.readOne(b);
	}

	@Transactional
	public void checkOut(int bookId, int branchId, int cardNo, BookCopies bc) throws ClassNotFoundException, SQLException {
		BookLoans bl = new BookLoans();
		bl.setBookId(bookId);
		bl.setBranchId(branchId);
		bl.setCardNo(cardNo);
		bldao.insertBookLoans(bl);
		bcdao.updateBookCopies(bc);
	}

	@Transactional
	public void checkIn(int bookId, int branchId, int cardNo, String dateOut, BookCopies bc) throws ClassNotFoundException, SQLException {
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
	}
}
