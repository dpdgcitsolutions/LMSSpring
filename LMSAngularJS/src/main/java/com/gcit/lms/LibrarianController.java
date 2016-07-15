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

import com.gcit.lms.service.LibrarianService;
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
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("librarian")
public class LibrarianController {

	private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);

	//	@Autowired
	//	AdministrativeaService aService;
	
	@Autowired
	LibrarianService lService;
	
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

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);


		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}

	@RequestMapping(value = "/selectBranch", method = RequestMethod.GET)
	public String selectBranch(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", lService);
		System.out.println(branchId);
		model.addAttribute("branchId", branchId);
		return "librarian/branch";
	}
	
	@RequestMapping(value = "/showBooks/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> showBooks(@PathVariable int branchId) throws ClassNotFoundException, SQLException {
		return bdao.readBooksByBranch(0, branchId);
	}
	
	@RequestMapping(value = "/prepareEditBookCopies", method = RequestMethod.GET)
	public String prepareEditBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId) {
		model.addAttribute("service", lService);
		model.addAttribute("bookId", bookId);
		model.addAttribute("branchId", branchId);
		return "librarian/editbookcopies";
	}
	
	@RequestMapping(value = "/editBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public String editBookCopies(@RequestBody BookCopies bc) throws ClassNotFoundException, SQLException {
		bcdao.updateBookCopies(bc);
		return "Updated Book Copies Successfully";
	}
	
	@RequestMapping(value = "/addBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public String addBookCopies( @RequestBody BookCopies bc ) throws ClassNotFoundException, SQLException {
		bcdao.insertBookCopies(bc);
		return "Added Book Copies Successfully";
	}
	
	@RequestMapping(value = "/extendDueDate", method = RequestMethod.POST, consumes = "application/json")
	public String extendDueDate(@RequestBody BookLoans bl) throws ClassNotFoundException, SQLException {
		if( bldao.readOne(bl) != null ){
			bldao.updateDueDate(bl);
			return "Extended Successfully";
		}
		else
			return "Record not found!";
	}
}
