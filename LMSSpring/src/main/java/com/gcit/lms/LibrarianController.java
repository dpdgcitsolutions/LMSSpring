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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.service.LibrarianService;
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
@Controller
@RequestMapping("librarian")
public class LibrarianController {

	private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);

	//	@Autowired
	//	AdministrativeaService aService;
	
	@Autowired
	LibrarianService lService;

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
	
	@RequestMapping(value = "/showBooks", method = RequestMethod.GET)
	public String showBooks(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", lService);
		model.addAttribute("branchId", branchId);
		return "librarian/showbooks";
	}
	
	@RequestMapping(value = "/prepareEditBookCopies", method = RequestMethod.GET)
	public String prepareEditBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId) {
		model.addAttribute("service", lService);
		model.addAttribute("bookId", bookId);
		model.addAttribute("branchId", branchId);
		return "librarian/editbookcopies";
	}
	
	@RequestMapping(value = "/editBookCopies", method = RequestMethod.POST)
	public String editBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId,
			@RequestParam int numCopies) {
		if( numCopies >= 0 )
		{
			try {
				BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numCopies);
				lService.editBookCopies(bc);
				model.addAttribute("message", "<div class='alert alert-success'>Copies updated sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Copies failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("service", lService);
		model.addAttribute("branchId", branchId);
		return "librarian/showbooks";
	}
	
	@RequestMapping(value = "/addBookCopies", method = RequestMethod.POST)
	public String addBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId,
			@RequestParam int numCopies ) {
		model.addAttribute("service", lService);
		model.addAttribute("branchId", branchId);
		if( numCopies >= 0 )
		{
			try {
				BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numCopies);
				lService.createBookCopies(bc);
				model.addAttribute("message", "<div class='alert alert-success'>Copies added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Copies failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		return "librarian/showbooks";
	}
}
