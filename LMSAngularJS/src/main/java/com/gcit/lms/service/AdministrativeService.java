package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorsDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenresDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;


@Service
public class AdministrativeService {
	
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
	
	@Transactional
	public void createAuthor(Author author) throws ClassNotFoundException, SQLException{
		adao.insertAuthor(author);
	}
	
	@Transactional
	public void createBorrower(Borrower bo) throws SQLException, ClassNotFoundException{
		bodao.insertBorrower(bo);
	}
	
	@Transactional
	public void editBorrower(Borrower bo) throws ClassNotFoundException, SQLException{
		bodao.updateBorrower(bo);;
	}
	
	@Transactional
	public void editAuthor(Author author) throws ClassNotFoundException, SQLException{
		adao.updateAuthor(author);
	}
	
	@Transactional
	public void editBook(Book b) throws ClassNotFoundException, SQLException {
		bdao.updateBook(b);
		badao.deleteBookAuthors(b);
		badao.insertBookAuthors(b);
		bgdao.deleteBookGenres(b);
		bgdao.insertBookGenres(b);
	}
	
	@Transactional
	public void createGenre(Genre g) throws ClassNotFoundException, SQLException{
		gdao.insertGenre(g);
	}
	
	@Transactional
	public void createPublisher(Publisher p) throws ClassNotFoundException, SQLException{
		pdao.insertPublisher(p);
	}
	
	@Transactional
	public void editBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.updateBookCopies(bc);			
	}
	
	@Transactional
	public void createBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.insertBookCopies(bc);			
	}
	
	@Transactional
	public void editBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		ldao.updateLibraryBranch(lib);
	}

	@Transactional
	public void createLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		ldao.insertLibraryBranch(lib);
	}
	
	@Transactional
	public void createBook(Book book) throws ClassNotFoundException, SQLException{
		Integer bookId = bdao.saveBookWithID(book);
		book.setBookId(bookId);
		badao.insertBookAuthors(book);
		bgdao.insertBookGenres(book);
	}
	
	public List<LibraryBranch> viewLibraryBranch() throws ClassNotFoundException, SQLException{
		return ldao.readAll();
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
	
	public List<Book> viewBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksNotInBranch(branchId);
	}
	
	public List<Book> viewBooksByAuthor(int pageNo, int authorId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksByAuthor(pageNo, authorId);
	}
	
	public List<Book> viewBooksByBranch(int pageNo, int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksByBranch(pageNo, branchId);
	}
	
	public List<Author> viewAuthorsByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		return adao.readAuthorsByBook(0, bookId);
	}

	public List<Genre> viewGenresByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		return gdao.readGenresByBook(bookId);
	}
	
	public List<Publisher> viewPublishersByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		return pdao.readPublisherByBook(bookId);
	}
	
	public List<Publisher> viewPublishers() throws ClassNotFoundException, SQLException{
		return pdao.readAll();	
	}
	
	public List<Genre> viewGenres() throws ClassNotFoundException, SQLException{
		return gdao.readAll();	
	}
	
//	public List<Genre> viewGenresFirstLevel() throws ClassNotFoundException, SQLException{
//		return gdao.readAllFirstLevel();	
//	}
	
	public List<Borrower> viewBorrowers() throws ClassNotFoundException, SQLException{
		return bodao.readAll();
	}
	
	public List<Author> viewAuthorsBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException{
		return adao.readBySearchString(searchString, pageNo);
	}
	
	public List<Book> viewBooksBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		return bdao.readBySearchString(searchString, pageNo);
	}
	
	
//	public List<Author> viewAuthorsFirstLevel(int pageNo) throws ClassNotFoundException, SQLException{
//		return adao.readAllFirstLevel(pageNo);
//	}
	
	public Integer getAuthorsCount() throws ClassNotFoundException, SQLException{
		return adao.getCount();
	}
	
	public Integer getBooksCount() throws ClassNotFoundException, SQLException{
		return bdao.getCount();
	}
	
	public List<Author> viewAuthors(int pageNo) throws ClassNotFoundException, SQLException{
		return adao.readAll(pageNo);
	}
	
	public List<Book> viewBooks(int pageNo) throws ClassNotFoundException, SQLException{
		return bdao.readAll(pageNo);
	}	
	
	public Author viewAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException{
		Author a = new Author();
		a.setAuthorId(authorID);
		return adao.readOne(a);
	}
	
	public LibraryBranch viewBranchById(Integer branchID) throws ClassNotFoundException, SQLException {
		LibraryBranch l = new LibraryBranch();
		l.setBranchId(branchID);
		return ldao.readOne(l);
	}
}
