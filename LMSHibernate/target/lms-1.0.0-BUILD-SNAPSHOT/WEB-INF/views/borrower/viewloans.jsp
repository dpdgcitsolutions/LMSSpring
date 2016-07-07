<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.BorrowerService" %>
    <%@ page import="com.gcit.lms.domain.Borrower" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%@ page import="com.gcit.lms.domain.BookLoans" %>
    <%BorrowerService service = new BorrowerService(); 
    int cardNo = (int)request.getAttribute("cardNo");
    Borrower b = new Borrower();
    b.setCardNo(cardNo);
    Borrower bo = service.viewBorrowerById(b);
    List<Book> books = bo.getBooks();
    List<BookLoans> bls = bo.getBookLoans();
    %>
<jsp:include page='include.html'></jsp:include>
<div class="users">
	<div class="container">
		<form role="form" class="form-horizontal" action="backToBookLoan" method="post">
		<input type="hidden" name="cardNo" value=<%=cardNo%>>
		<div class="form-group" style="padding-left:15px">	      
	        <button type="submit" class="btn btn-primary">Back</button>
	    </div>
		</form>
${message }
<h3>List of Borrowed Books</h3>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Book Title</th>
			<th>Author</th>
			<th>Branch Name</th>
			<th>Date Out</th>
			<th>Due Date</th>
		</tr>
	<thead>
	<tbody>
	<%for(BookLoans bl : bls){ %>
		<tr>
			<td><%=service.viewBookByID(bl.getBookId()).getTitle() %></td>
			<td><%=service.viewBookByID(bl.getBookId()).getAuthors().get(0).getAuthorName() %></td>
			<td><%=service.viewBranchById(bl.getBranchId()).getBranchName() %></td>
			<td><%=bl.getDateOut() %></td>
			<td><%=bl.getDueDate() %></td>
			<td>
				<form role="form" class="form-horizontal" action="confirmCheckIn" method="post">
					<input type="hidden" name="cardNo" value=<%=cardNo%>>
					<input type="hidden" name="bookId" value=<%=bl.getBookId()%>>
					<input type="hidden" name="branchId" value=<%=bl.getBranchId()%>>
					<input type="hidden" name="dateOut" value=<%=bl.getDateOut()%>>
					<div class="form-group" style="padding-left:15px">	      
				        <button type="submit" class="btn btn-success">SELECT</button>
				    </div>
				</form>
			</td>
			</tr>
	<%} %>
	</tbody>	
</table>

<div id="editBranchModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>


</div>
</div>