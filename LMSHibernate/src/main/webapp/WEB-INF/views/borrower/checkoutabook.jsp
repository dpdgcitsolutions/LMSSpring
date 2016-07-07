<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.BorrowerService" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch"%>
    
    <%BorrowerService service = new BorrowerService(); 
    int cardNo = (int)request.getAttribute("cardNo");
    int branchId = (int)request.getAttribute("branchId");
    List<Book> books = service.viewAvailableBooks(branchId);
    %>

<jsp:include page='include.html'></jsp:include>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Borrower! Choose your action<p>
	</div>
</div>
<body>
		<form role="form" class="form-horizontal" action="backToViewBranches" method="post">
		<input type="hidden" name="cardNo" value=<%=cardNo%>>
		<input type="hidden" name="branchId" value=<%=branchId%>>
		<div class="form-group" style="padding-left:15px">	      
	        <button type="submit" class="btn btn-primary">Back</button>
	    </div>
		</form>
${message}

	      <h3>List of Books</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Book Title</th>
				<th>Author</th>
				<th>Genre</th>
				<th>Publisher</th>
				<th>Select</th>
			</tr>
		</thead>
		<tbody>
		<% for(Book b : books) {%>
			<tr>
				<td><%=b.getTitle() %></td>
				<td><%=b.getAuthors().get(0).getAuthorName() %></td>
				<td><%=b.getGenres().get(0).getGenre_name() %></td>
				<td><%=b.getPublisher().getPublisherName() %></td>
				<td>
					<form role="form" class="form-horizontal" action="confirmCheckOut" method="post">
					<input type="hidden" name="cardNo" value=<%=cardNo%>>
					<input type="hidden" name="bookId" value=<%=b.getBookId()%>>
					<input type="hidden" name="branchId" value=<%=branchId%>>
					<div class="form-group" style="padding-left:15px">	      
				        <button type="submit" class="btn btn-success">SELECT</button>
				    </div>
					</form>
				</td>
			</tr>
			<% } %>
		</tbody>
	</table>
</body>
</html>