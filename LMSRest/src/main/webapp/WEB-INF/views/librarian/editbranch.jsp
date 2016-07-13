<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.LibrarianService"%>
<%@ page import="com.gcit.lms.domain.LibraryBranch"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	LibrarianService service = new LibrarianService();
	LibraryBranch lib = service.viewBranchById(branchId);
	
%>
<!DOCTYPE html>
<h3>Hello Librarian! Edit Branch details</h3>
	<form action="editBranch" method="post">
		Edit Branch Name:
		<input type="text" name="branchName" value='<%=lib.getBranchName()%>'></br>
		Edit Branch Address:
		<input type="text" name="branchAddress" value='<%=lib.getBranchAddress()%>'></br>
		<input type="hidden" name="branchId" value=<%=branchId%>>
		<button type="submit">Edit Branch</button>
	</form>
<script>
$(document).ready(function()
	{
	    $('.modal').on('hidden.bs.modal', function(e)
	    { 
	        $(this).removeData();
	    }) ;
	});
</script>