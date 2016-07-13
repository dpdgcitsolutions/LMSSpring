<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdministrativeService"%>
<%@ page import="com.gcit.lms.domain.Author"%>
<%
	Integer authorId = Integer.parseInt(request.getAttribute("authorId").toString());
	AdministrativeService service = (AdministrativeService)request.getAttribute("service");
	Author a = service.viewAuthorByID(authorId);
	
%>
<!DOCTYPE html>
<div class="users">
	<div class="container">
<h3>Hello Admin! Edit Author details</h3>
	<form role="form" class="form-horizontal" action="editAuthor" method="post">
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="author">Edit Author Name:</label>
	      <div class="col-sm-6">
	      	<input type="text" class="form-control" name="authorName" value='<%=a.getAuthorName()%>'>
		  </div>
	    </div>
	    <div class="col-sm-4">
	    	<input type="hidden" name="authorId" value=<%=a.getAuthorId()%>>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-warning">Edit Author</button>
	      </div>
	    </div>
	</form>
<!-- 	<form action="editAuthor" method="post"> -->
<!-- 		Edit Author Name: -->
<%-- 		<input type="text" name="authorName" value='<%=a.getAuthorName()%>'> --%>
<%-- 		<input type="hidden" name="authorId" value=<%=a.getAuthorId()%>> --%>
<!-- 		<button type="submit">Edit Author</button> -->
<!-- 	</form> -->
</div>
</div>
<script>
$(document).ready(function()
		{
		    $('.modal').on('hidden.bs.modal', function(e)
		    { 
		        $(this).removeData();
		    }) ;
		});
</script>