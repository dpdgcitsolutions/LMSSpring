<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch" %>
    <%
    AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
    List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
    libs = aService.viewLibraryBranch();
    %>
<%@ include file="include.html" %>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Admin! Choose your branch<p>
	</div>
</div>

<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="../admin">Back</a><br/>
	
<h3>List of Branches</h3>
<table class="table table-hover">
	<thead>
	<tr>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Select</th>
		<th>Edit Branch</th>
		<th>Delete Branch</th>
	</tr>
	<thead>
	<tbody>
	<%for(LibraryBranch l: libs){ %>
		<tr>
			<td><%=l.getBranchName() %></td>
			<td><%=l.getBranchAddress() %></td>
			<td><button type="button" class="btn btn-success" onclick="javascript:location.href='selectBranch?branchId=<%=l.getBranchId()%>'">SELECT</button></td>
			<td><button type="button" class="btn btn-warning" data-toggle="modal" 
			data-target="#editBranchModal" href='prepareEditBranch?branchId=<%=l.getBranchId() %>'>EDIT</button></td>
			<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteBranch?branchId=<%=l.getBranchId()%>'">DELETE</button></td>
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