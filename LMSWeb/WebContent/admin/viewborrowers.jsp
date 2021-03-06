<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Borrower" %>
    <%AdministrativeService service = new AdministrativeService(); 
    List<Borrower> bs = new ArrayList<Borrower>();
    bs = service.viewBorrowers();
    %>
<jsp:include page='include.html'></jsp:include>
<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="../admin">Back</a><br/>
	
<h3>List of Branches</h3>
<table class="table table-hover">
	<thead>
	<tr>
		<th>Borrower Card Number</th>
		<th>Borrower Name</th>
		<th>Borrower Address</th>
		<th>Borrower Phone</th>
		<th>Edit Borrower</th>
		<th>Delete Borrower</th>
	</tr>
	<thead>
	<tbody>
	<%for(Borrower bo: bs){ %>
		<tr>
			<td><%=bo.getCardNo() %></td>
			<td><%=bo.getName() %></td>
			<td><%=bo.getAddress() %></td>
			<td><%=bo.getPhone() %></td>
			<td><button type="button" class="btn btn-warning" data-toggle="modal" 
			data-target="#editBranchModal" href='editbranch.jsp?branchId=<%=bo.getCardNo() %>'>EDIT</button></td>
			<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteBranch?branchId=<%=bo.getCardNo()%>'">DELETE</button></td>
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