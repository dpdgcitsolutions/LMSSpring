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

import com.gcit.lms.service.BorrowerService;
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

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("borrower")
public class BorrowerController {

	private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);

	@Autowired
	BorrowerService bService;
	
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
	
	@RequestMapping(value = "/showBooks/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> showBooks(@PathVariable int branchId) throws ClassNotFoundException, SQLException {
		return bdao.readBooksByBranch(0, branchId);
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST, consumes = "application/json")
	public String signIn(@RequestBody Borrower bo) throws ClassNotFoundException, SQLException {
		if( bodao.readOne(bo) == null )
			return "Invalid Card No";
		else
			return "Successfully Signed In";
	}
	
	@RequestMapping(value = "/checkOut", method = RequestMethod.POST, consumes = "application/json")
	public String checkOut(@RequestBody BookLoans bl) throws ClassNotFoundException, SQLException {
		int bookId = bl.getBookId();
		int branchId = bl.getBranchId();
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		BookCopies bcp = bcdao.readOne(bc);
		if( bcp != null && bcp.getNoOfCopies() > 0 ){
			bcp.setNoOfCopies(bcp.getNoOfCopies()-1);
			bcdao.updateBookCopies(bcp);
			bldao.insertBookLoans(bl);
			return "Check Out Successfully";
		}
		else
			return "Book is not in branch or no copies available";
	}
	
	@RequestMapping(value = "/viewBookLoans", method = RequestMethod.POST, produces = "application/json")
	public List<BookLoans> viewBookLoans(@RequestBody Borrower bo) throws ClassNotFoundException, SQLException {
		return bldao.readAllByCardNo(bo.getCardNo());
	}
	
	@RequestMapping(value = "/checkIn", method = RequestMethod.POST, consumes = "application/json")
	public String checkIn(@RequestBody BookLoans bl) throws ClassNotFoundException, SQLException {
		if( bldao.readOne(bl) != null ){
			bldao.updateDateIn(bl);
			BookCopies bc = new BookCopies();
			bc.setBookId(bl.getBookId());
			bc.setBranchId(bl.getBranchId());
			BookCopies bcp = bcdao.readOne(bc);
			bcp.setNoOfCopies(bcp.getNoOfCopies() + 1);
			bcdao.updateBookCopies(bcp);
			return "Check In Successfully";
		}
		else
			return "Record not found!";
	}
}
