package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministrativeService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/admin","/admin/addStuffs", "/admin/editAuthor","/admin/addBranch", 
	"/admin/pageAuthors", "/admin/pageBooks", "/admin/editBranch", "/admin/editBookCopies", "/admin/addBookCopies", "/admin/addBorrower", "/admin/searchAuthors", "/admin/searchBooks",
	"/admin/editBook"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }
    AdministrativeService service = new AdministrativeService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "admin.jsp";
		switch (mapping) {
		case "/admin/editAuthor":
			forwardPath = prepareEditAuthor(request);
			break;
		case "/admin/deleteAuthor":
			forwardPath = prepareEditAuthor(request);
			break;
		case "/admin/pageAuthors":
			pageAuthors(request, response);
//			forwardPath = "viewauthors.jsp";
			break;
		case "/admin/pageBooks":
			pageBooks(request, response);
//			forwardPath = "viewauthors.jsp";
			break;
		case "/admin/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/admin/searchBooks":
			searchBooks(request, response);
			break;
		case "/admin":
			forwardPath = "admin/admin.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
			rd.forward(request, response);
		default:
			break;
		}
//		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
//		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "admin.jsp";
		switch (mapping) {
		case "/admin/addStuffs":
			forwardPath = addStuffs(request);
			break;
		case "/admin/editAuthor":
			forwardPath = editAuthor(request);
			break;
		case "/admin/addBookCopies":
			forwardPath = addBookCopies(request);
			break;
		case "/admin/editBookCopies":
			forwardPath = editBookCopies(request);
			break;
		case "/admin/addBranch":
			forwardPath = addBranch(request);
			break;
		case "/admin/editBranch":
			forwardPath = editBranch(request);
			break;
		case "/admin/addBorrower":
			forwardPath = addBorrower(request);
			break;
		case "/admin/editBook":
			forwardPath = editBook(request);
			break;
		default:
			break;
		}		
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private String addBorrower(HttpServletRequest request) {
		String borrowerName = request.getParameter("borrowerName");
		if( borrowerName != null ) {
			Borrower bo = new Borrower();
			bo.setName(borrowerName);
			bo.setAddress(request.getParameter("borrowerAddress"));
			bo.setPhone(request.getParameter("borrowerPhone"));
			try {
				service.createBorrower(bo);
				request.setAttribute("message", "<div class='alert alert-success'>Borrower added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Borrower failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewborrowers.jsp";
		}
		return "addborrower.jsp";
	}

	private String addBookCopies(HttpServletRequest request) {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		Integer numCopies;
		try {
			numCopies = Integer.parseInt(request.getParameter("numCopies"));
		} catch (NumberFormatException e) {
			request.setAttribute("message", "<div class='alert alert-danger'>Not a number!</div>");
		    return "addbook.jsp?branchId=" + branchId;
		}
		if( numCopies >= 0 )
		{
			try {
				BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numCopies);
				service.createBookCopies(bc);
				request.setAttribute("message", "<div class='alert alert-success'>Copies added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Copies failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "showbooks.jsp?branchId=" + branchId;
		}			
		return "addbook.jsp";
	}
	
	private String editBookCopies(HttpServletRequest request) {
		String bookName = request.getParameter("bookName");
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		Integer numCopies;
		try {
			numCopies = Integer.parseInt(request.getParameter("numCopies"));
		} catch (NumberFormatException e) {
			request.setAttribute("message", "<div class='alert alert-danger'>Not a number!</div>");
		    return "showbooks.jsp?branchId=" + branchId;
		}
		if( numCopies >= 0 )
		{
			try {
				BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numCopies);
				service.editBookCopies(bc);
				request.setAttribute("message", "<div class='alert alert-success'>Copies updated sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Copies failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "showbooks.jsp?branchId=" + branchId;
		}
		return "showbooks.jsp?branchId=" + branchId;
	}
	
	private void searchAuthors(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Author> authors = new ArrayList<Author>();
		String searchString = request.getParameter("searchString");
		System.out.println("Search String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			if( searchString == null )
				searchString = "";
			authors = service.viewAuthorsBySearchString(searchString, 1);
			request.setAttribute("authors", authors);
			str.append("<thead><tr><th>Author Name</th><th>Book Title</th><th>Edit Author</th><th>Delete Author</th></tr></thead><tbody>");
			for (Author a : authors) {
				str.append("<tr><td >"+a.getAuthorName()+"</td>");
				str.append("<td>"+a.getBooks().get(0).getTitle()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#authorModal' href='editauthor.jsp?authorId="+a.getAuthorId()+"'>EDIT</button></td>");
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
	
	private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Book> books = new ArrayList<Book>();
		String searchString = request.getParameter("searchString");
		System.out.println("Search String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			if( searchString == null )
				searchString = "";
			books = service.viewBooksBySearchString(searchString, 1);
			request.setAttribute("books", books);
			str.append("<thead><tr><th>Book Title</th><th>Author</th><th>Genre</th><th>Publisher</th><th>Edit Book</th><th>Delete Book</th></tr></thead><tbody>");
			for (Book b : books) {
				str.append("<tr><td>"+b.getTitle()+"</td>");
				str.append("<td>"+b.getAuthors().get(0).getAuthorName()+"</td>");
				str.append("<td>"+b.getGenres().get(0).getGenre_name()+"</td>");
				str.append("<td>"+b.getPublisher().getPublisherName()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#bookModal' href='editbook.jsp?bookId=" + b.getBookId() + "'>EDIT</button></td>");
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
	
	private void pageBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<Book> books = new ArrayList<Book>();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		String searchString = request.getParameter("searchString");
		if( searchString == null ){
			searchString = "";
			System.out.println("Page String: NULL");
		}
			
		System.out.println("Page String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			books = service.viewBooksBySearchString(searchString, pageNo);
			str.append("<thead><tr><th>Book Title</th><th>Author</th><th>Genre</th><th>Publisher</th><th>Edit Book</th><th>Delete Book</th></tr></thead><tbody>");
			for (Book b : books) {
				str.append("<tr><td >"+b.getTitle()+"</td>");
				str.append("<td>"+b.getAuthors().get(0).getAuthorName()+"</td>");
				str.append("<td >"+b.getGenres().get(0).getGenre_name()+"</td>");
				str.append("<td >"+b.getPublisher().getPublisherName()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#bookModal' href='editbook.jsp?bookId=" + b.getBookId() + "'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+b.getBookId()+"'>DELETE</button></td></tr>");
			}
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}

	
	private void pageAuthors(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Author> authors = new ArrayList<Author>();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		String searchString = request.getParameter("searchString");
		if( searchString == null ){
			searchString = "";
			System.out.println("Page String: NULL");
		}
			
		System.out.println("Page String: " + searchString);
		StringBuffer str = new StringBuffer();
		try {
			authors = service.viewAuthorsBySearchString(searchString, pageNo);
			str.append("<thead><tr><th>Author Name</th><th>Book Title</th><th>Edit Author</th><th>Delete Author</th></tr></thead><tbody>");
			for (Author a : authors) {
				str.append("<tr><td >"+a.getAuthorName()+"</td>");
				str.append("<td>"+a.getBooks().get(0).getTitle()+"</td>");
				str.append("<td><button type='button' class='btn btn-sm btn-warning' data-toggle='modal' data-target='#authorModal' href='editauthor.jsp?authorId="+a.getAuthorId()+"'>EDIT</button></td>");
				str.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"'>DELETE</button></td></tr>");
			}
			response.getWriter().append(str);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append(str);
		}
	}
	
	private String editBranch(HttpServletRequest request) {
		String branchName = request.getParameter("branchName");
		if( branchName != null ) {
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			String branchAddress = request.getParameter("branchAddress");
			try {
				LibraryBranch lib = new LibraryBranch();
				lib.setBranchId(branchId);
				lib.setBranchName(branchName);
				lib.setBranchAddress(branchAddress);
				service.editBranch(lib);
				request.setAttribute("message", "<div class='alert alert-success'>Branch updated sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Branch failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewbranches.jsp";
		}
		return "viewbranches.jsp";
	}

	private String addBranch(HttpServletRequest request) {
		String branchName = request.getParameter("branchName");
		if( branchName != null ) {
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchName(branchName);
			lib.setBranchAddress(request.getParameter("branchAddress"));
			try {
				service.createLibraryBranch(lib);
				request.setAttribute("message", "<div class='alert alert-success'>Branch added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Branch failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewbranches.jsp";
		}
		return "addbranch.jsp";
	}
	
	private String editBook(HttpServletRequest request) {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String bookTitle = request.getParameter("bookTitle");
		if( bookTitle != null )	{
			// publisher
			int pubId = Integer.parseInt(request.getParameter("pubId"));
			Publisher p = new Publisher();
			p.setPublisherId(pubId);
			// author
			int authorId = Integer.parseInt(request.getParameter("authorId"));
			Author a = new Author();
			a.setAuthorId(authorId);
			List<Author> authors = new ArrayList<Author>();
			authors.add(a);
			// genre
			int genreId = Integer.parseInt(request.getParameter("genreId"));
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
				service.editBook(b);
				request.setAttribute("message", "<div class='alert alert-success'>Book updated sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Book updated failed!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewbooks.jsp";
			
		}
		return null;
	}

	private String addStuffs(HttpServletRequest request) {
		// Author
		String authorName = request.getParameter("authorName");
		if(authorName != null)
		{
			Author author = new Author();
			author.setAuthorName(authorName);
			try {
				service.createAuthor(author);
				request.setAttribute("message", "<div class='alert alert-success'>Author added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Author failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewauthors.jsp";
		}
		// Book
		String bookTitle = request.getParameter("bookTitle");
		if( bookTitle != null )
		{
			// publisher
			int pubId = Integer.parseInt(request.getParameter("pubId"));
			Publisher p = new Publisher();
			p.setPublisherId(pubId);
			// author
			int authorId = Integer.parseInt(request.getParameter("authorId"));
			Author a = new Author();
			a.setAuthorId(authorId);
			List<Author> authors = new ArrayList<Author>();
			authors.add(a);
			// genre
			int genreId = Integer.parseInt(request.getParameter("genreId"));
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
				service.createBook(b);
				request.setAttribute("message", "<div class='alert alert-success'>Book added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Book failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewbooks.jsp";
		}
		// Publisher
		String pubName = request.getParameter("publisherName");
		if( pubName != null )
		{
			Publisher p = new Publisher();
			p.setPublisherName(pubName);
			p.setPublisherAddress(request.getParameter("publisherAddress"));
			p.setPublisherPhone(request.getParameter("publisherPhone"));
			try {
				service.createPublisher(p);
				request.setAttribute("message", "<div class='alert alert-success'>Publisher added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Publisher failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewpublishers.jsp";
		}		
		// Genre
		String genreName = request.getParameter("genreName");
		if( genreName != null )
		{
			Genre g = new Genre();
			g.setGenre_name(genreName);
			try {
				service.createGenre(g);
				request.setAttribute("message", "<div class='alert alert-success'>Genre added sucessfully!</div>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "<div class='alert alert-danger'>Genre failed sucessfully!</div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "viewgenres.jsp";
		}
		return "addstuffs.jsp";
	}
	
	private String prepareEditAuthor(HttpServletRequest request) {
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		if(authorId!=null && !("").equals(authorId)){
			Integer authorID = Integer.parseInt(authorId);
			try {
				author = service.viewAuthorByID(authorID);
				request.setAttribute("author", author);
				return "editauthor.jsp";
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "viewauthors.jsp";
			}
		}
		return null;
	}
	
	private String editAuthor(HttpServletRequest request) {
		String authorName = request.getParameter("authorName");
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setAuthorId(Integer.parseInt(authorId));
		try {
			service.editAuthor(author);
			request.setAttribute("message", "Author edit sucessfully");
			return "viewauthors.jsp";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "Author failed sucessfully");
			return "addstuffs.jsp";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "addstuffs.jsp";
		}
	}

}
