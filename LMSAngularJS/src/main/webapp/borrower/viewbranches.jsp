<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.BorrowerService" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch" %>
    <%@ page import="com.gcit.lms.domain.Borrower" %>
    <%BorrowerService service = new BorrowerService(); 
    List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
    libs = service.viewLibraryBranch();
    int cardNo = (int)request.getAttribute("cardNo");
    Borrower b = new Borrower();
    b.setCardNo(cardNo);
    Borrower bo = service.viewBorrowerById(b);
    %>
<jsp:include page='include.html'></jsp:include>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello <%=bo.getName() %>! Choose your branch<p>
	</div>
</div>

<div class="users">
	<div class="container">
		<form role="form" class="form-horizontal" action="backToBookLoan" method="post">
		<input type="hidden" name="cardNo" value=<%=cardNo%>>
		<div class="form-group" style="padding-left:15px">	      
	        <button type="submit" class="btn btn-primary">Back</button>
	    </div>
		</form>
	
<h3>List of Branches</h3>
<table class="table table-hover">
	<thead>
	<tr>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Select</th>
	</tr>
	<thead>
	<tbody>
	<%for(LibraryBranch l: libs){ %>
		<tr>
			<td><%=l.getBranchName() %></td>
			<td><%=l.getBranchAddress() %></td>
			<td>
				<form role="form" class="form-horizontal" action="checkOutABook" method="post">
				<input type="hidden" name="cardNo" value=<%=cardNo%>>
				<input type="hidden" name="branchId" value=<%=l.getBranchId()%>>
				<div class="form-group" style="padding-left:15px">	      
			        <button type="submit" class="btn btn-success">SELECT</button>
			    </div>
				</form>
				</td>
		</tr>
	<%} %>
	</tbody>	
</table>


</div>
</div>
