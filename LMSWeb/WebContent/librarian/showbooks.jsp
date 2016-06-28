<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.gcit.lms.domain.LibraryBranch"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.domain.Book" %>
<%@ page import="com.gcit.lms.domain.BookCopies" %>
<%@ page import="com.gcit.lms.service.LibrarianService" %>
<%
LibrarianService service = new LibrarianService();
Integer branchId = Integer.parseInt(request.getParameter("branchId"));

LibraryBranch l = service.viewBranchById(branchId);
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
		<% for(Book b : l.getBooks()) {%>
			<tr>
				<td><%=b.getTitle() %></td>
				<td><%=service.viewBookCopiesByID(b.getBookId(), branchId).getNoOfCopies() %></td>
				<td><button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#bookCopiesModal"
							href='editbookcopies.jsp?branchId=<%=branchId %>&bookId=<%=b.getBookId() %>'>EDIT</button></td>
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
