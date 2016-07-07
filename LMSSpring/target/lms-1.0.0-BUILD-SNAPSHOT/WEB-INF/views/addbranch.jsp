<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%@ page import="com.gcit.lms.domain.Author" %>
    <%@ page import="com.gcit.lms.domain.Genre" %>
    <%@ page import="com.gcit.lms.domain.Publisher" %>
    <%AdministrativeService service = new AdministrativeService(); 
    List<Author> authors = new ArrayList<Author>();
    authors = service.viewAuthors(1);
    List<Genre> genres = new ArrayList<Genre>();
    genres = service.viewGenres();
    List<Publisher> publishers = new ArrayList<Publisher>();
    publishers = service.viewPublishers();%>
<%@ include file="include.html" %>

<body>
	<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="../admin">Back</a><br/>
	
	
	<h3 class="title">Library Branch</h3>
	<form role="form" class="form-horizontal" action="addBranch" method="post">
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="branch">Name:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="branchName" placeholder="Enter Branch Name">
		  </div>
		  <label class="control-label col-sm-2" for="branch">Address:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="branchAddress" placeholder="Enter Branch Address">
		  </div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Branch</button>
	      </div>
	    </div>
	</form><br/>
	
	</div>
</div>
</body>