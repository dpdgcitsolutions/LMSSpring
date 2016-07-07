<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.LibrarianService" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%@ page import="com.gcit.lms.domain.Author" %>
    <%@ page import="com.gcit.lms.domain.Genre" %>
    <%@ page import="com.gcit.lms.domain.Publisher" %>
    <%LibrarianService service = new LibrarianService(); 
    Integer branchId = Integer.parseInt(request.getParameter("branchId"));
    List<Book> books = service.viewBooksNotInBranch(branchId);
%>
    
<jsp:include page='include.html'></jsp:include>
<div class="users">
	<div class="container">
<a style="font-size:20px;" href="branch.jsp?branchId=<%=branchId%>">Back</a><br/>
${message}
<form role="form" class="form-horizontal" action="addBookCopies" method="post">
    <div class="form-group">
    	<label class="control-label col-sm-2" for="bookTitle">Choose Book Title:</label>
	      <div class="col-sm-10">
		      <select id="bookId" name="bookId" >
				<% for( Book b : books ) { %>
					<option value=<%=b.getBookId() %>><%=b.getTitle()%></option>
				<% } %>
			  </select>
		</div>
	</div>
	<div class="form-group">
	      <label class="control-label col-sm-2" for="numCopies">Book Copies:</label>
		      <div class="col-sm-10">
		      	<input type="text" class="form-control" name="numCopies" placeholder="Enter Book Copies">
			  </div>
			  <input type="hidden" name="branchId" value=<%=branchId%>>
	</div>
	<div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-primary">Add Book Copies</button>
      </div>
    </div>
</form>

</div>
</div>