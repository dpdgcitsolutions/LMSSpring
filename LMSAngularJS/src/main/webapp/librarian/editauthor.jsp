<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdministrativeService"%>
<%@ page import="com.gcit.lms.domain.Author"%>
<%
	Integer authorId = Integer.parseInt(request.getParameter("authorId"));
	AdministrativeService service = new AdministrativeService();
	Author a = service.viewAuthorByID(authorId);
	
%>
<!DOCTYPE html>
<h3>Hello Admin! Edit Author details</h3>
	<form action="editAuthor" method="post">
		Edit Author Name:
		<input type="text" name="authorName" value='<%=a.getAuthorName()%>'>
		<input type="hidden" name="authorId" value=<%=a.getAuthorId()%>>
		<button type="submit">Edit Author</button>
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