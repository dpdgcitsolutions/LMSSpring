<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.gcit.lms.domain.LibraryBranch"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.domain.Book" %>
<%@ page import="com.gcit.lms.domain.BookCopies" %>
<%@ page import="com.gcit.lms.service.LibrarianService" %>
<%
	LibrarianService service = (LibrarianService)request.getAttribute("service");
	Integer branchId = Integer.parseInt(request.getAttribute("branchId").toString());
	LibraryBranch l = service.viewBranchById(branchId);
	List<Book> books = service.viewBooksByBranch(0, branchId);
%>
<%@ include file="include.html" %>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Librarian! Choose your book<p>
	</div>
</div>

<div class="users">
    <div class="container">
		<a href="selectBranch?branchId=<%=branchId %>" style="font-size:20px;">Back</a><br/><br/>
${message }
	<h3>List of Books</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Book Title</th>
				<th>Number of Copies</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
		</thead>
		<tbody>
		<% for(Book b : books) {%>
			<tr>
				<td><%=b.getTitle() %></td>
				<td><%=service.viewBookCopiesByID(b.getBookId(), branchId).getNoOfCopies() %></td>
				<td><button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#bookCopiesModal"
							href='prepareEditBookCopies?branchId=<%=branchId %>&bookId=<%=b.getBookId() %>'>EDIT</button></td>
				<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=b.getBookId()%>'">DELETE</button></td>
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
