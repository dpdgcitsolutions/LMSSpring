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

import com.gcit.lms.service.AdministrativeService;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	//	@Autowired
	//	AdministrativeaService aService;
	
	@Autowired
	AdministrativeService aService;


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
		model.addAttribute("service", aService);
		return "addstuffs";
	}
	
	@RequestMapping(value = "/prepareEditAuthor", method = RequestMethod.GET)
	public String prepareEditAuthor(Locale locale, Model model, @RequestParam int authorId) {
		model.addAttribute("service", aService);
		model.addAttribute("authorId", authorId);
		return "editauthor";
	}
	
	@RequestMapping(value = "/prepareEditBook", method = RequestMethod.GET)
	public String prepareEditBook(Locale locale, Model model, @RequestParam int bookId) {
		model.addAttribute("service", aService);
		model.addAttribute("bookId", bookId);
		return "editbook";
	}
	
	@RequestMapping(value = "/prepareEditBookCopies", method = RequestMethod.GET)
	public String prepareEditBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		model.addAttribute("bookId", bookId);
		model.addAttribute("branchId", branchId);
		return "editbookcopies";
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.GET)
	public String addBook(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		model.addAttribute("branchId", branchId);
		return "addbook";
	}
	
	@RequestMapping(value = "/addBookCopies", method = RequestMethod.POST)
	public String addBookCopies(Locale locale, Model model, @RequestParam int bookId, @RequestParam int branchId,
			@RequestParam int numCopies ) {
		model.addAttribute("service", aService);
		model.addAttribute("branchId", branchId);
		if( numCopies >= 0 )
		{
			try {
				BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numCopies);
				aService.createBookCopies(bc);
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
		return "showbooks";
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
				aService.editBookCopies(bc);
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
		model.addAttribute("service", aService);
		model.addAttribute("branchId", branchId);
		return "showbooks";
	}
	
	@RequestMapping(value = "/selectBranch", method = RequestMethod.GET)
	public String branch(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		System.out.println(branchId);
		model.addAttribute("branchId", branchId);
		return "branch";
	}
	
	@RequestMapping(value = "/editBook", method = RequestMethod.POST)
	public String editBook(Locale locale, Model model, @RequestParam String bookTitle, @RequestParam int bookId, @RequestParam int pubId,
			@RequestParam int authorId, @RequestParam int genreId) {
		model.addAttribute("service", aService);
		if( bookTitle != null && !bookTitle.equals(""))	{
			// publisher
			Publisher p = new Publisher();
			p.setPublisherId(pubId);
			// author
			Author a = new Author();
			a.setAuthorId(authorId);
			List<Author> authors = new ArrayList<Author>();
			authors.add(a);
			// genre
			Genre g = new Genre();
			g.setGenre_id(genreId);
			List<Genre> genres = new ArrayList<Genre>();
			genres.add(g);
			// book
			Book b = new Book();
			b.setTitle(bookTitle);
			b.setAuthors(authors);
			b.setPublisher(p);
			b.setGenres(genres);
			b.setBookId(bookId);
			try {
				aService.editBook(b);
				model.addAttribute("message", "<div class='alert alert-success'>Book updated sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Book updated failed!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "viewbooks";
	}
	
	@RequestMapping(value = "/editAuthor", method = RequestMethod.POST)
	public String editAuthor(Locale locale, Model model, @RequestParam String authorName, @RequestParam int authorId) {
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setAuthorId(authorId);
		model.addAttribute("service", aService);
		try {
			aService.editAuthor(author);
			model.addAttribute("message", "Author edit sucessfully");
			return "viewauthors";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("message", "Author failed sucessfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewauthors";
	}
	
	@RequestMapping(value = "/pageAuthors", method = RequestMethod.GET)
	public void pageAuthors(Locale locale, Model model, @RequestParam String searchString, @RequestParam int pageNo, HttpServletResponse response) throws IOException {
		List<Author> authors = new ArrayList<Author>();
		if( searchString == null ){
			searchString = "";
			System.out.println("Page String: NULL");
		}
		System.out.println("Page String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			authors = aService.viewAuthorsBySearchString(searchString, pageNo);
			str.append("<thead><tr><th>Author Name</th><th>Book Title</th><th>Edit Author</th><th>Delete Author</th></tr></thead><tbody>");
			for (Author a : authors) {
				str.append("<tr><td >"+a.getAuthorName()+"</td>");
				str.append("<td>"+aService.viewBooksByAuthor(0, a.getAuthorId()).get(0).getTitle()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#authorModal' href='prepareEditAuthor?authorId="+a.getAuthorId()+"'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"'>DELETE</button></td></tr>");
			}
			str.append("</tbody>");
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}
	
	@RequestMapping(value = "/pageBooks", method = RequestMethod.GET)
	public void pageBooks(Locale locale, Model model, @RequestParam String searchString, @RequestParam int pageNo, HttpServletResponse response) throws IOException {
		List<Book> books = new ArrayList<Book>();
		if( searchString == null ){
			searchString = "";
			System.out.println("Page String: NULL");
		}
		System.out.println("Page String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			books = aService.viewBooksBySearchString(searchString, pageNo);
			str.append("<thead><tr><th>Book Title</th><th>Author</th><th>Genre</th><th>Publisher</th><th>Edit Book</th><th>Delete Book</th></tr></thead><tbody>");
			for (Book b : books) {
				str.append("<tr><td >"+b.getTitle()+"</td>");
				str.append("<td>"+aService.viewAuthorsByBook(0, b.getBookId()).get(0).getAuthorName()+"</td>");
				str.append("<td >"+aService.viewGenresByBook(0, b.getBookId()).get(0).getGenre_name()+"</td>");
				str.append("<td >"+aService.viewPublishersByBook(0, b.getBookId()).get(0).getPublisherName()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#bookModal' href='prepareEditBook?bookId=" + b.getBookId() + "'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+b.getBookId()+"'>DELETE</button></td></tr>");
			}
			str.append("</tbody>");
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}
	
	@RequestMapping(value = "/searchBooks", method = RequestMethod.GET)
	public void searchBooks(Locale locale, Model model, @RequestParam String searchString, HttpServletResponse response) throws IOException {
		List<Book> books = new ArrayList<Book>();
		System.out.println("Search String: " + searchString);
		StringBuffer str = new StringBuffer();
		try{
			if( searchString == null )
				searchString = "";
			books = aService.viewBooksBySearchString(searchString, 1);
			model.addAttribute("books", books);
			str.append("<thead><tr><th>Book Title</th><th>Author</th><th>Genre</th><th>Publisher</th><th>Edit Book</th><th>Delete Book</th></tr></thead><tbody>");
			for (Book b : books) {
				str.append("<tr><td >"+b.getTitle()+"</td>");
				str.append("<td>"+aService.viewAuthorsByBook(0, b.getBookId()).get(0).getAuthorName()+"</td>");
				str.append("<td >"+aService.viewGenresByBook(0, b.getBookId()).get(0).getGenre_name()+"</td>");
				str.append("<td >"+aService.viewPublishersByBook(0, b.getBookId()).get(0).getPublisherName()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#bookModal' href='prepareEditBook?bookId=" + b.getBookId() + "'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+b.getBookId()+"'>DELETE</button></td></tr>");
			}
			str.append("</tbody>");
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}
	
	@RequestMapping(value = "/searchAuthors", method = RequestMethod.GET)
	public void searchAuthors(Locale locale, Model model, @RequestParam String searchString, HttpServletResponse response) throws IOException {
		List<Author> authors = new ArrayList<Author>();
		System.out.println("Search String: " + searchString);
		StringBuffer str = new StringBuffer();
		try{
			if( searchString == null )
				searchString = "";
			authors = aService.viewAuthorsBySearchString(searchString, 1);
			model.addAttribute("authors", authors);
			str.append("<thead><tr><th>Author Name</th><th>Book Title</th><th>Edit Author</th><th>Delete Author</th></tr></thead><tbody>");
			for (Author a : authors) {
				str.append("<tr><td >"+a.getAuthorName()+"</td>");
				str.append("<<td>"+aService.viewBooksByAuthor(0, a.getAuthorId()).get(0).getTitle()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#authorModal' href='prepareEditAuthor?authorId="+a.getAuthorId()+"'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"'>DELETE</button></td></tr>");
			}
			str.append("</tbody>");
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}


	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST)
	public String addBorrowerFunction(Locale locale, Model model, @RequestParam String borrowerName, @RequestParam String borrowerAddress, @RequestParam String borrowerPhone) {
		if( borrowerName != null && !borrowerName.equals("")) {
			Borrower bo = new Borrower();
			bo.setName(borrowerName);
			bo.setAddress(borrowerAddress);
			bo.setPhone(borrowerPhone);
			System.out.println(borrowerName + " " + borrowerAddress + " " + borrowerPhone + " from home controller");
			try {

				aService.createBorrower(bo);
				model.addAttribute("message", "<div class='alert alert-success'>Borrower added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Borrower failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			return "addborrower";
		}
		return "addborrower";
	}

	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
	public String addAuthor(Locale locale, Model model, @RequestParam String authorName) {
		// Author		
		if(authorName != null)
		{
			Author author = new Author();
			author.setAuthorName(authorName);
			try {
				aService.createAuthor(author);
				model.addAttribute("message", "<div class='alert alert-success'>Author added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Author failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("service", aService);
			return "viewauthors";
		}
		model.addAttribute("service", aService);
		return "addstuffs";
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addBook(Locale locale, Model model, @RequestParam String bookTitle, 
			@RequestParam int pubId, @RequestParam int authorId, @RequestParam int genreId) {
		// Book
		if( bookTitle != null && !bookTitle.equals("") )
		{
			// publisher
			Publisher p = new Publisher();
			p.setPublisherId(pubId);
			// author
			Author a = new Author();
			a.setAuthorId(authorId);
			List<Author> authors = new ArrayList<Author>();
			authors.add(a);
			// genre
			Genre g = new Genre();
			g.setGenre_id(genreId);
			List<Genre> genres = new ArrayList<Genre>();
			genres.add(g);
			// book
			Book b = new Book();
			b.setTitle(bookTitle);
			b.setAuthors(authors);
			b.setPublisher(p);
			b.setGenres(genres);
			try {
				aService.createBook(b);
				model.addAttribute("message", "<div class='alert alert-success'>Book added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Book failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("service", aService);
			return "viewbooks";
		}
		model.addAttribute("service", aService);
		return "addstuffs";
	}
	
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
	public String addPublisher(Locale locale, Model model, @RequestParam String publisherName, 
			@RequestParam String publisherAddress, @RequestParam String publisherPhone) {
		// Publisher
		if( publisherName != null )
		{
			Publisher p = new Publisher();
			p.setPublisherName(publisherName);
			p.setPublisherAddress(publisherAddress);
			p.setPublisherPhone(publisherPhone);
			try {
				aService.createPublisher(p);
				model.addAttribute("message", "<div class='alert alert-success'>Publisher added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Publisher failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("service", aService);
			return "viewpublishers";
		}		
		model.addAttribute("service", aService);
		return "addstuffs";
	}
	
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST)
	public String addGenre(Locale locale, Model model, @RequestParam String genreName) {
		// Genre
		if( genreName != null )
		{
			Genre g = new Genre();
			g.setGenre_name(genreName);
			try {
				aService.createGenre(g);
				model.addAttribute("message", "<div class='alert alert-success'>Genre added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "<div class='alert alert-danger'>Genre failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("service", aService);
			return "viewgenres";
		}
		model.addAttribute("service", aService);
		return "addstuffs";
	}

	@RequestMapping(value = "/addBranch", method = RequestMethod.GET)
	public String addBranch(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "addbranch";
	}

	@RequestMapping(value = "/addBorrower", method = RequestMethod.GET)
	public String addBorrower(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "addborrower";
	}

	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET)
	public String viewAuthors(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewauthors";
	}

	@RequestMapping(value = "/showBooks", method = RequestMethod.GET)
	public String showBooks(Locale locale, Model model, @RequestParam int branchId) {
		model.addAttribute("service", aService);
		model.addAttribute("branchId", branchId);
		return "showbooks";
	}
	
	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET)
	public String viewBooks(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewbooks";
	}

	@RequestMapping(value = "/viewPublishers", method = RequestMethod.GET)
	public String viewPublishers(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewpublishers";
	}

	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET)
	public String viewGenres(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewgenres";
	}

	@RequestMapping(value = "/viewBranches", method = RequestMethod.GET)
	public String viewBranches(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewbranches";
	}

	@RequestMapping(value = "/viewBorrowers", method = RequestMethod.GET)
	public String viewBorrowers(Locale locale, Model model) {
		model.addAttribute("service", aService);
		return "viewborrowers";
	}

}
