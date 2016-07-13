package com.gcit.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.service.AdministrativeService;
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

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	//	@Autowired
	//	AdministrativeaService aService;
	
	@Autowired
	AdministrativeService aService;

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

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);


		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "admin";
	}

	
	@RequestMapping(value = "/addStuffs", method = RequestMethod.GET)
	public String addStuffs(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "admin/addstuffs";
	}
	
	@RequestMapping(value = "/prepareEditAuthor", method = RequestMethod.GET)
	public String prepareEditAuthor(Locale locale, Model model, @RequestParam int authorId) {
		model.addAttribute("service", aService);
		model.addAttribute("authorId", authorId);
		return "admin/editauthor";
	}
	
	@RequestMapping(value = "/prepareEditBook", method = RequestMethod.GET)
	public String prepareEditBook(Locale locale, Model model, @RequestParam int bookId) {
		model.addAttribute("service", aService);
		model.addAttribute("bookId", bookId);
		return "admin/editbook";
	}
	
	@RequestMapping(value = "/prepareEditBookCopies", method = RequestMethod.GET)
	public String prepareEditBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		model.addAttribute("bookId", bookId);
		model.addAttribute("branchId", branchId);
		return "admin/editbookcopies";
	}
	
	@RequestMapping(value = "/prepareEditBranch", method = RequestMethod.GET)
	public String prepareEditBranch(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("branchId", branchId);
		model.addAttribute("service", aService);
		return "admin/editbranch";
	}
	
	@RequestMapping(value = "/prepareEditBorrower", method = RequestMethod.GET)
	public String prepareEditBorrower(Locale locale, Model model, @RequestParam int borrowerId) {
		model.addAttribute("service", aService);
		model.addAttribute("borrowerId", borrowerId);
		return "admin/editborrower";
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.GET)
	public String addBook(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		model.addAttribute("branchId", branchId);
		return "admin/addbook";
	}
	
	@RequestMapping(value = "/editBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public String editBookCopies(@RequestBody BookCopies bc) throws ClassNotFoundException, SQLException {
		bcdao.updateBookCopies(bc);
		return "Updated Book Copies Successfully";
	}
	
	@RequestMapping(value = "/selectBranch", method = RequestMethod.GET)
	public String branch(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		System.out.println(branchId);
		model.addAttribute("branchId", branchId);
		return "admin/branch";
	}
	
	@RequestMapping(value = "/editBranch", method = RequestMethod.POST, consumes = "application/json")
	public String editBranch(@RequestBody LibraryBranch branch) throws ClassNotFoundException, SQLException {
		ldao.updateLibraryBranch(branch);
		return "Updated Branch Successfully";
	}
	
	@RequestMapping(value = "/editBook", method = RequestMethod.POST, consumes = "application/json")
	public String editBook(@RequestBody Book book) throws ClassNotFoundException, SQLException {
		bdao.updateBook(book);
		return "Updated Book Successfully";
	}
	
	@RequestMapping(value = "/editAuthor", method = RequestMethod.POST, consumes = "application/json")
	public String editAuthor(@RequestBody Author author) throws ClassNotFoundException, SQLException {
		adao.updateAuthor(author);
		return "Updated Author Successfully";
	}
	
	@RequestMapping(value = "/pageAuthors/{searchString}/{pageNo}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Author> pageAuthors(@PathVariable String searchString, @PathVariable int pageNo) throws IOException, ClassNotFoundException, SQLException {
		return adao.readBySearchString(searchString, 0);
	}
	
	@RequestMapping(value = "/pageBooks/{searchString}/{pageNo}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Book> pageBooks(@PathVariable String searchString, @PathVariable int pageNo) throws IOException, ClassNotFoundException, SQLException {
		List<Book> books = bdao.readBySearchString(searchString, pageNo);
		for( Book b : books ){
			b.setAuthors(adao.readAuthorsByBook(0, b.getBookId()));
			b.setGenres(gdao.readGenresByBook(b.getBookId()));
		}
		return books;
	}
	
	@RequestMapping(value = "/viewBooks/{pageNo}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Book> viewBooks(@PathVariable int pageNo) throws ClassNotFoundException, SQLException {
		List<Book> books = bdao.readBySearchString("", pageNo);
		for( Book b : books ){
			b.setAuthors(adao.readAuthorsByBook(0, b.getBookId()));
			b.setGenres(gdao.readGenresByBook(b.getBookId()));
		}
		return books; 
	}
	
	// check
	@RequestMapping(value = "/searchBooks/{searchString}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Book> searchBooks(@PathVariable String searchString) throws ClassNotFoundException, SQLException {
		List<Book> books = bdao.readBySearchString("", 0);
		for( Book b : books ){
			b.setAuthors(adao.readAuthorsByBook(0, b.getBookId()));
			b.setGenres(gdao.readGenresByBook(b.getBookId()));
		}
		return books; 
	}
	
	// check
	@RequestMapping(value = "/searchAuthors/{searchString}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Author> searchAuthors(@PathVariable String searchString) throws ClassNotFoundException, SQLException{
		return adao.readBySearchString(searchString, 0);
	}


	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST, consumes = "application/json")
	public String addBorrowerFunction(@RequestBody Borrower bo) throws ClassNotFoundException, SQLException {
		bodao.insertBorrower(bo);
		return "Borrower Added Successfully";
	}
	
	@RequestMapping(value = "/addBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public String addBookCopies( @RequestBody BookCopies bc ) throws ClassNotFoundException, SQLException {
		bcdao.insertBookCopies(bc);
		return "Added Book Copies Successfully";
	}

	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes = "application/json")
	public String addAuthor(@RequestBody Author author) throws ClassNotFoundException, SQLException {
		// Author		
		adao.insertAuthor(author);
		return "Author Added Successfully";
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes = "application/json")
	public String addBook(@RequestBody Book book) throws ClassNotFoundException, SQLException {
		// Book
		Integer bookId = bdao.saveBookWithID(book);
		book.setBookId(bookId);
		badao.insertBookAuthors(book);
		bgdao.insertBookGenres(book);
		return "Book Added Successfully";
	}
	
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST, consumes = "application/json")
	public String addPublisher(@RequestBody Publisher p) throws ClassNotFoundException, SQLException {
		// Publisher
		pdao.insertPublisher(p);
		return "Publisher Added Successfully";
	}
	
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST, consumes = "application/json")
	public String addGenre(@RequestBody Genre g) throws ClassNotFoundException, SQLException {
		gdao.insertGenre(g);
		return "Genre Added Successfully";
	}

	@RequestMapping(value = "/addBranch", method = RequestMethod.GET)
	public String addBranch(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "admin/addbranch";
	}

	@RequestMapping(value = "/addBorrower", method = RequestMethod.GET)
	public String addBorrower(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "admin/addborrower";
	}

	@RequestMapping(value = "/viewAuthors/{pageNo}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public List<Author> viewAuthors(@PathVariable int pageNo) throws ClassNotFoundException, SQLException {
		return adao.readAll(pageNo);
	}
	
	@RequestMapping(value = "/viewPublishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> viewPublishers() throws ClassNotFoundException, SQLException {
		return pdao.readAll();
	}

	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET, produces = "application/json")
	public List<Genre> viewGenres() throws ClassNotFoundException, SQLException {
		return gdao.readAll();
	}

	@RequestMapping(value = "/viewBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> viewBranches() throws ClassNotFoundException, SQLException {
		return ldao.readAll();
	}

	@RequestMapping(value = "/viewBorrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> viewBorrowers() throws ClassNotFoundException, SQLException {
		return bodao.readAll();
	}

	@RequestMapping(value = "/showBooks/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> showBooks(@PathVariable int branchId) throws ClassNotFoundException, SQLException {
		return bdao.readBooksByBranch(0, branchId);
	}
}
