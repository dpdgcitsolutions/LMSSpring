<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdministrativeService"%>
<%@ page import="com.gcit.lms.domain.Author"%>
<%@ page import="com.gcit.lms.domain.Book"%>
<%@ page import="com.gcit.lms.domain.Genre" %>
<%@ page import="com.gcit.lms.domain.Publisher" %>
<%
	AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
	Integer bookId = Integer.parseInt(request.getAttribute("bookId").toString());
	List<Author> authors = new ArrayList<Author>();
	authors = aService.viewAuthors(0);
	List<Genre> genres = new ArrayList<Genre>();
	genres = aService.viewGenres();
	List<Genre> bookGenres = new ArrayList<Genre>();
	bookGenres = aService.viewGenresByBook(0, bookId);
	List<Publisher> publishers = new ArrayList<Publisher>();
	publishers = aService.viewPublishers();
	Book b = aService.viewBookByID(bookId);
	List<Author> bookAuthors = new ArrayList<Author>();
	bookAuthors = aService.viewAuthorsByBook(0, bookId);
%>
<!DOCTYPE html>
<div class="users">
	<div class="container">
<h3>Hello Admin! Edit Book details</h3>
	<form role="form" class="form-horizontal" action="editBook" method="post">
		<div class="form-group">
	      <label class="control-label col-sm-2" for="book">Title:</label>
	      <div class="col-sm-6">
	      	<input type="text" class="form-control" name="bookTitle" value='<%=b.getTitle()%>'">
	      </div>
	      <div class="col-sm-4">
	      	<input type="hidden" name="bookId" value=<%=bookId%>>
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="publishers">Publisher:</label>
	      <div class="col-sm-10">
	      <select id="pubId" name="pubId" >
			<% for( Publisher p : publishers ) { %>
				<% if(b.getPublisher().getPublisherId() == p.getPublisherId() ) {%>
					<option value=<%=p.getPublisherId() %> selected><%=p.getPublisherName()%></option>
				<%} else{%>				
					<option value=<%=p.getPublisherId() %>><%=p.getPublisherName()%></option>
				<%} %>
			<% } %>
		  </select>
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="authors">Author:</label>
	      <div class="col-sm-10">
	      <select id="authorId" name="authorId">
			<% for( Author a : authors ) { %>
				<% if( bookAuthors.contains(a) ) {%>
					<option value=<%=a.getAuthorId() %> selected><%=a.getAuthorName()%></option>
				<%} else { %>
					<option value=<%=a.getAuthorId() %>><%=a.getAuthorName()%></option>
			<% }} %>
		  </select>
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="genres">Genre:</label>
	      <div class="col-sm-10">
	      <select id="genreId" name="genreId">
			<% for( Genre g : genres) { %>
				<% if( bookGenres.contains(g) ) { %>
					<option value=<%=g.getGenre_id() %> selected><%=g.getGenre_name()%></option>
				<%} else{ %>
					<option value=<%=g.getGenre_id() %>><%=g.getGenre_name()%></option>
			<% } }%>
		</select>
		</div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-warning">Edit Book</button>
	      </div>
	    </div>
	</form><br/>
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