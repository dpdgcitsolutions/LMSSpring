<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdministrativeService"%>
<%@ page import="com.gcit.lms.domain.Book"%>
<%@ page import="com.gcit.lms.domain.BookCopies"%>
<%
AdministrativeService service = new AdministrativeService();
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	BookCopies bc = service.viewBookCopiesByID(bookId, branchId);
	Book b = service.viewBookByID(bookId);
%>
<!DOCTYPE html>
<h3>Hello Librarian! Edit book details</h3>
	<form action="editBookCopies" method="post">
		Edit Book Name:
		<input type="text" name="bookName" value='<%=b.getTitle()%>'></br>
		Edit Number of Copies:
		<input type="text" name="numCopies" value='<%=bc.getNoOfCopies()%>'>
		<input type="hidden" name="bookId" value='<%=bc.getBookId()%>'>
		<input type="hidden" name="branchId" value='<%=bc.getBranchId()%>'>
		<button type="submit">Edit Book Copies</button>
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