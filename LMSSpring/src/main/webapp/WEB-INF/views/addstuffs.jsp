<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%@ page import="com.gcit.lms.domain.Author" %>
    <%@ page import="com.gcit.lms.domain.Genre" %>
    <%@ page import="com.gcit.lms.domain.Publisher" %>
    <%AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
    List<Author> authors = new ArrayList<Author>();
    authors = aService.viewAuthors(0);
    List<Genre> genres = new ArrayList<Genre>();
    genres = aService.viewGenres();
    List<Publisher> publishers = new ArrayList<Publisher>();
    publishers = aService.viewPublishers();%>
    
<%@ include file="include.html" %>

<body>
	<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="admin">Back</a><br/>	
	
	
	<h3 class="title">Author</h3>
	<form role="form" class="form-horizontal" action="addAuthor" method="post">
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="author">Name:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="authorName" placeholder="Enter Author Name">
		  </div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Author</button>
	      </div>
	    </div>
	</form><br/>
	
	<h3 class="title">Book</h3>
	<form role="form" class="form-horizontal" action="addBook" method="post">
		<div class="form-group">
	      <label class="control-label col-sm-2" for="book">Title:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="bookTitle" placeholder="Enter Book Title">
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="publishers">Publisher:</label>
	      <div class="col-sm-10">
	      <select id="pubId" name="pubId" >
			<% for( Publisher p : publishers ) { %>
				<option value=<%=p.getPublisherId() %>><%=p.getPublisherName()%></option>
			<% } %>
		  </select>
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="authors">Author:</label>
	      <div class="col-sm-10">
	      <select id="authorId" name="authorId">
			<% for( Author a : authors ) { %>
				<option value=<%=a.getAuthorId() %>><%=a.getAuthorName()%></option>
			<% } %>
		  </select>
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="genres">Genre:</label>
	      <div class="col-sm-10">
	      <select id="genreId" name="genreId">
			<% for( Genre g : genres) { %>
				<option value=<%=g.getGenre_id() %>><%=g.getGenre_name()%></option>
			<% } %>
		</select>
		</div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Book</button>
	      </div>
	    </div>
	</form><br/>
	
	<h3 class="title">Genre</h3>
	<form role="form" class="form-horizontal" action="addGenre" method="post">
	 	<div class="form-group">
	      <label class="control-label col-sm-2" for="genre">Genre:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="genreName" placeholder="Enter Genre Name">
	      </div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Genre</button>
	      </div>
	    </div>
	</form><br/>
	
	<h3 class="title">Publisher</h3>
	<form role="form" class="form-horizontal" action="addPublisher" method="post">
		<div class="form-group">
	      	<label class="control-label col-sm-2" for="publisher">Name:</label>
	      	<div class="col-sm-10">
	      		<input type="text" class="form-control" name="publisherName" placeholder="Enter Publisher Name">
	      	</div>
	      	<label class="control-label col-sm-2" for="publisher">Address:</label>
	      	<div class="col-sm-10">
	      		<input type="text" class="form-control" name="publisherAddress" placeholder="Enter Publisher Address">
	      	</div>
	      	<label class="control-label col-sm-2" for="publisher">Phone:</label>
	      	<div class="col-sm-10">
	      		<input type="text" class="form-control" name="publisherPhone" placeholder="Enter Publisher Phone">
	      	</div>
	     </div>
	     <div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Publisher</button>
	      </div>
	    </div>
	</form><br/>
	</div>
</div>
</body>