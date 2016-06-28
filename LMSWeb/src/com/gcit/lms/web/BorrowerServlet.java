package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/borrower", "/checkCardNo" ,"/checkOut" ,"/backToBookLoan" ,"/checkOutABook", "/confirmCheckOut", "/backToViewBranches", "/checkIn", "/confirmCheckIn"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    BorrowerService service = new BorrowerService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "borrower.jsp";
		RequestDispatcher rd;
		switch(mapping){
			case "/borrower":
				forwardPath = "borrower/borrower.jsp";
				rd = request.getRequestDispatcher(forwardPath);
				rd.forward(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mapping = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "../borrower";
		int cardNo, branchId, bookId;
		switch(mapping){
			case "/checkCardNo":
				forwardPath = checkCardNo(request);
				break;				
			case "/checkOut":
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				request.setAttribute("cardNo", cardNo);
				forwardPath = "borrower/viewbranches.jsp";
				break;
			case "/backToBookLoan":
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				request.setAttribute("cardNo", cardNo);
				forwardPath = "borrower/bookloan.jsp";
				break;
			case "/backToViewBranches":
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				branchId = Integer.parseInt(request.getParameter("branchId"));
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
				forwardPath = "borrower/viewbranches.jsp";
				break;
			case "/checkOutABook":
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				branchId = Integer.parseInt(request.getParameter("branchId"));
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
				forwardPath = "borrower/checkoutabook.jsp";
				break;
			case "/confirmCheckOut":
				forwardPath = checkOut(request);
				break;
			case "/checkIn":
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				request.setAttribute("cardNo", cardNo);
				forwardPath = "borrower/viewloans.jsp";
				break;
			case "/confirmCheckIn":
				forwardPath = checkIn(request);
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private String checkIn(HttpServletRequest request) {
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String dateOut = request.getParameter("dateOut");
		request.setAttribute("cardNo", cardNo);
		try {
			BookCopies bc = service.viewBookCopiesByID(bookId, branchId);
			bc.setNoOfCopies(bc.getNoOfCopies()+1);
			service.checkIn(bookId, branchId, cardNo, dateOut, bc);
			request.setAttribute("message", "<div class='alert alert-success'>Check in sucessfully!</div>");
			return "borrower/viewloans.jsp";
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Check in failed sucessfully!</div>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Check in failed sucessfully!</div>");
		}
		return "borrower/viewloans.jsp";
	}

	private String checkOut(HttpServletRequest request) {
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		request.setAttribute("cardNo", cardNo);
		request.setAttribute("branchId", branchId);
		try {
			BookCopies bc = service.viewBookCopiesByID(bookId, branchId);
			bc.setNoOfCopies(bc.getNoOfCopies()-1);
			service.checkOut(bookId, branchId, cardNo, bc);
			request.setAttribute("message", "<div class='alert alert-success'>Check out sucessfully!</div>");
			return "borrower/checkoutabook.jsp";
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Check out failed sucessfully!</div>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Check out same book more than once on same day not allowed!</div>");
		}
		return null;
	}

	private String checkCardNo(HttpServletRequest request) {
		Integer cardNo;
		try {
			cardNo = Integer.parseInt(request.getParameter("cardNo"));
		} catch (NumberFormatException e) {
			request.setAttribute("message", "<div class='alert alert-danger'>Not a number!</div>");
		    return "borrower/borrower.jsp";
		}
		try {
			Borrower bo = new Borrower();
			bo.setCardNo(cardNo);
			Borrower b = service.checkCardNo(bo);
			if( b != null ){
				request.setAttribute("message", "<div class='alert alert-success'>Login sucessfully!</div>");
				request.setAttribute("cardNo", cardNo);
				return "borrower/bookloan.jsp";
			}
			else{
				request.setAttribute("message", "<div class='alert alert-danger'>Login failed!</div>");
				return "borrower/borrower.jsp";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "<div class='alert alert-danger'>Login  sucessfully!</div>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "borrower/borrower.jsp";
	}

}
