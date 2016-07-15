<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.gcit.lms.domain.LibraryBranch"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.domain.Book" %>
<%@ page import="com.gcit.lms.domain.BookLoans" %>
<%@ page import="com.gcit.lms.domain.Borrower" %>
<%@ page import="com.gcit.lms.service.LibrarianService" %>
<%
LibrarianService service = new LibrarianService();
Integer branchId = Integer.parseInt(request.getParameter("branchId"));
LibraryBranch l = service.viewBranchById(branchId);
List<Borrower> bos = service.viewBorrowerByBranch(l);
List<BookLoans> bls = service.viewBookLoansByBranch(branchId);
%>
<jsp:include page='include.html'></jsp:include>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Librarian! Choose your book<p>
	</div>
</div>

<div class="users">
    <div class="container">
		<a href="branch.jsp?branchId=<%=branchId %>" style="font-size:20px;">Back</a><br/><br/>
${message }
	<h3>List of Borrowers</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Card No</th>
				<th>Name</th>
				<th>Book Title</th>
				<th>Date Out</th>
				<th>Due Date</th>
				<th>Extend Due Date</th>
			</tr>
		</thead>
		<tbody>
		<% for(BookLoans bl : bls) {%>
			<tr>
				<td><%=service.viewBorrowerByCardNo(bl.getCardNo()).getCardNo() %></td>
				<td><%=service.viewBorrowerByCardNo(bl.getCardNo()).getName() %></td>
				<td><%=service.viewBookByID(bl.getBookId()).getTitle() %></td>
				<td><%=bl.getDateOut() %></td>
				<td><%=bl.getDueDate() %></td>
				<td>
					<form role="form" class="form-horizontal" action="confirmExtend" method="post">
					<input type="hidden" name="cardNo" value=<%=service.viewBorrowerByCardNo(bl.getCardNo()).getCardNo()%>>
					<input type="hidden" name="bookId" value=<%=service.viewBookByID(bl.getBookId()).getBookId()%>>
					<input type="hidden" name="branchId" value=<%=branchId%>>
					<input type="hidden" name="dateOut" value=<%=bl.getDateOut()%>>
					<div class="form-group" style="padding-left:15px">	      
				        <button type="submit" class="btn btn-success">EXTEND</button>
				    </div>
					</form>
				</td>
			</tr>
			<% } %>
		</tbody>
	</table>
	
<div id="bookCopiesModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
</div>
</div>
