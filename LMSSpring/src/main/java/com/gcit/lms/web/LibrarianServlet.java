package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet({"/librarian", "/librarian/addBookCopies", "/librarian/editBookCopies", "/editBranch", "/librarian/confirmExtend"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    LibrarianService service = new LibrarianService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "index.jsp";
		switch (mapping) {
			case "/chooseBranch":
				forwardPath = showBooks(request);
				break;
			case "/librarian":
				forwardPath = "/librarian/librarian.jsp";
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "";
		switch (mapping) {
			case "/librarian/addBookCopies":
				forwardPath = addBookCopies(request);
				break;
			case "/librarian/editBookCopies":
				forwardPath = editBookCopies(request);
				break;
			case "/librarian/confirmExtend":
				forwardPath = extendDueDate(request);
				break;
			case "/editBranch":
				forwardPath = editBranch(request);
			default:
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
//		doGet(request, response);
	}

	private String extendDueDate(HttpServletRequest request) {
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String dateOut = request.getParameter("dateOut");
		request.setAttribute("branchId", branchId);
		try {
			service.extendDueDate(bookId, branchId, cardNo, dateOut);
			request.setAttribute("message", "<div class='alert alert-success'>Extend sucessfully!</div>");
			return "showborrowers.jsp?branchId=" + branchId;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Extend failed sucessfully!</div>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Extend failed sucessfully!</div>");
		}
		return "showborrowers.jsp?branchId=" + branchId;
	}
	
	private String showBooks(HttpServletRequest request) {
		String branchId = request.getParameter("branchId");
		LibraryBranch lib = new LibraryBranch();
		if(branchId!=null && !("").equals(branchId)){
			Integer branchID = Integer.parseInt(branchId);
			try {
				lib = service.viewBranchById(branchID);
				request.setAttribute("branch", lib);
				return "showbooks.jsp";
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "librarian.jsp";
			}
		}
		return null;
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
			return "librarian/librarian.jsp";
		}
		return "librarian/librarian.jsp";
	}

	private String editBookCopies(HttpServletRequest request) {
		String bookName = request.getParameter("bookName");
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		Integer numCopies;
		try {
			numCopies = Integer.parseInt(request.getParameter("numCopies"));
		} catch (NumberFormatException e) {
			request.setAttribute("message", "<div class='alert alert-danger'>Copies updated failed!</div>");
		    return "showbook.jsp?branchId=" + branchId;
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

	private String addBookCopies(HttpServletRequest request) {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		Integer numCopies;
		try {
			numCopies = Integer.parseInt(request.getParameter("numCopies"));
		} catch (NumberFormatException e) {
		    System.out.println("Wrong number");
		    return "addbook.jsp";
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

}
