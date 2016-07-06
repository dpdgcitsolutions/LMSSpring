<%@ include file="include.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Author" %>
	<%
	AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
	Integer count = aService.getAuthorsCount();
	Integer pageCount = 0;
	if(count!=null && count>0){
		int rem = count % 10;
		if(rem == 0){
			pageCount = count/10;
		}else{
			pageCount = count/10+1;
		}
	}
	List<Author> authors = new ArrayList<Author>();
	authors = aService.viewAuthorsBySearchString("", 1);
    %>

<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="admin">Back</a><br/>
	

<script>
function pageAuthors(val){
	$.ajax({url: "pageAuthors",data: { pageNo: val, searchString : $('#searchString').val()},
		})
		  .done(function( data ) {
		    $('#authorsTable').html(data);
		  });
}
	
	function searchAuthors(){
		$.ajax({url: "searchAuthors", data: { searchString : $('#searchString').val() },
			})
			  .done(function( data ) {
			    $('#authorsTable').html(data);
			  });
	}
</script>

<nav>
  <ul class="pagination">
    <%for(int i=1; i<=pageCount;i++){ %>
    <li><a id="pageNo" onclick="pageAuthors(<%=i%>)"><%=i%></a></li>
	<%} %>
  </ul>
</nav>

<form action="searchAuthor" method="post">
	<input type="text" name="searchString" id="searchString" placeholder="Enter Author Name" onkeyup="searchAuthors()"/>
</form>
			
	
<h3>List of Authors</h3>
<table class="table table-hover" id="authorsTable">
	<thead>
		<tr>
			<th>Author Name</th>
			<th>Book Title</th>
			<th>Edit Author</th>
			<th>Delete Author</th>
		</tr>
	<thead>
	<tbody>
	<%for(Author a: authors){ %>
		<tr>
			<td><%=a.getAuthorName() %></td>
			<td><%=aService.viewBooksByAuthor(0, a.getAuthorId()).get(0).getTitle() %></td>
			<td><button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#authorModal"
							href='prepareEditAuthor?authorId=<%=a.getAuthorId()%>'>EDIT</button></td>
			<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>'">DELETE</button></td>
			</tr>
	<%} %>
	</tbody>	
</table>

<div id="authorModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>

</div>
</div>