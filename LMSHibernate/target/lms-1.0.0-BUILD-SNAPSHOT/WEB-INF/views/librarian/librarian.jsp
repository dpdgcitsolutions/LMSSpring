<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.LibrarianService" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch" %>
    <%LibrarianService service = new LibrarianService(); 
    List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
    libs = service.viewLibraryBranch();
    %>
<jsp:include page='include.html'></jsp:include>
<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="index.jsp">Back</a><br/>
	
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
			<td><button type="button" class="btn btn-success" onclick="javascript:location.href='librarian/branch.jsp?branchId=<%=l.getBranchId()%>'">SELECT</button></td>
			<td><button type="button" class="btn btn-warning" data-toggle="modal" 
			data-target="#editBranchModal" href='librarian/editbranch.jsp?branchId=<%=l.getBranchId() %>'>EDIT</button></td>
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