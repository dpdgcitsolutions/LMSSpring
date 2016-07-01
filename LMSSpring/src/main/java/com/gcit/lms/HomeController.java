package com.gcit.lms;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	AdministrativeService service = new AdministrativeService();
	
	
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
	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public String test(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "Test";
//	} 
//	
//	@RequestMapping(value = "/test", method = RequestMethod.POST)
//	public String testTwo(Locale locale, Model model, @RequestParam Integer name) {
//		model.addAttribute("name", name);
//		
//		return "Test";
//	} 
	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String goToAdminPage(Locale locale, Model model) {
		return "admin";
	}
	
	@RequestMapping(value = "/addStuffs", method = RequestMethod.GET)
	public String addStuffs(Locale locale, Model model) {
		return "addstuffs";
	}
	
	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST)
	public String addBorrowerFunction(Locale locale, Model model, @RequestParam String borrowerName, @RequestParam String borrowerAddress, @RequestParam String borrowerPhone) {
		if( borrowerName != null || !borrowerName.equals("")) {
			Borrower bo = new Borrower();
			bo.setName(borrowerName);
			bo.setAddress(borrowerAddress);
			bo.setPhone(borrowerPhone);
			try {
				service.createBorrower(bo);
				model.addAttribute("message", "<div class='alert alert-success'>Borrower added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Borrower failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "addborrower";
		}
		return "addborrower";
	}
	
	@RequestMapping(value = "/addStuffs", method = RequestMethod.POST)
	public String addStuffsFunction(Locale locale, Model model, @RequestParam String authorName) {
		// Author		
		if(authorName != null)
		{
			Author author = new Author();
			author.setAuthorName(authorName);
			try {
				service.createAuthor(author);
				model.addAttribute("message", "<div class='alert alert-success'>Author added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Author failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			model.addAttribute("service", service);
			return "viewauthors";
		}
//		// Book
//		String bookTitle = request.getParameter("bookTitle");
//		if( bookTitle != null )
//		{
//			// publisher
//			int pubId = Integer.parseInt(request.getParameter("pubId"));
//			Publisher p = new Publisher();
//			p.setPublisherId(pubId);
//			// author
//			int authorId = Integer.parseInt(request.getParameter("authorId"));
//			Author a = new Author();
//			a.setAuthorId(authorId);
//			List<Author> authors = new ArrayList<Author>();
//			authors.add(a);
//			// genre
//			int genreId = Integer.parseInt(request.getParameter("genreId"));
//			Genre g = new Genre();
//			g.setGenre_id(genreId);
//			List<Genre> genres = new ArrayList<Genre>();
//			genres.add(g);
//			// book
//			Book b = new Book();
//			b.setTitle(bookTitle);
//			b.setAuthors(authors);
//			b.setPublisher(p);
//			b.setGenres(genres);
//			try {
//				service.createBook(b);
//				request.setAttribute("message", "<div class='alert alert-success'>Book added sucessfully!</div>");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				request.setAttribute("message", "<div class='alert alert-danger'>Book failed sucessfully!</div>");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return "viewbooks.jsp";
//		}
//		// Publisher
//		String pubName = request.getParameter("publisherName");
//		if( pubName != null )
//		{
//			Publisher p = new Publisher();
//			p.setPublisherName(pubName);
//			p.setPublisherAddress(request.getParameter("publisherAddress"));
//			p.setPublisherPhone(request.getParameter("publisherPhone"));
//			try {
//				service.createPublisher(p);
//				request.setAttribute("message", "<div class='alert alert-success'>Publisher added sucessfully!</div>");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				request.setAttribute("message", "<div class='alert alert-danger'>Publisher failed sucessfully!</div>");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return "viewpublishers.jsp";
//		}		
//		// Genre
//		String genreName = request.getParameter("genreName");
//		if( genreName != null )
//		{
//			Genre g = new Genre();
//			g.setGenre_name(genreName);
//			try {
//				service.createGenre(g);
//				request.setAttribute("message", "<div class='alert alert-success'>Genre added sucessfully!</div>");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				request.setAttribute("message", "<div class='alert alert-danger'>Genre failed sucessfully!</div>");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return "viewgenres.jsp";
//		}
		return "addstuffs";
	}
	
	@RequestMapping(value = "/addBranch", method = RequestMethod.GET)
	public String addBranch(Locale locale, Model model) {
		return "addbranch";
	}
	
	@RequestMapping(value = "/addBorrower", method = RequestMethod.GET)
	public String addBorrower(Locale locale, Model model) {
		return "addborrower";
	}
	
	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	public String viewAuthors(Locale locale, Model model) {
		return "viewauthors";
	}
	
	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET)
	public String viewBooks(Locale locale, Model model) {
		return "viewbooks";
	}
	
	@RequestMapping(value = "/viewPublishers", method = RequestMethod.GET)
	public String viewPublishers(Locale locale, Model model) {
		return "viewpublishers";
	}
	
	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET)
	public String viewGenres(Locale locale, Model model) {
		return "viewgenres";
	}
	
	@RequestMapping(value = "/viewBranches", method = RequestMethod.GET)
	public String viewBranches(Locale locale, Model model) {
		return "viewbranches";
	}
	
	@RequestMapping(value = "/viewBorrowers", method = RequestMethod.GET)
	public String viewBorrowers(Locale locale, Model model) {
		return "viewborrowers";
	}
	
}
