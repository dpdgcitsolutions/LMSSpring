<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Book" %>
    <%AdministrativeService service = new AdministrativeService(); 
    List<Book> books = new ArrayList<Book>();
    Integer count = service.getBooksCount();
	Integer pageCount = 0;
	if(count!=null && count>0){
		int rem = count % 10;
		if(rem == 0){
			pageCount = count/10;
		}else{
			pageCount = count/10+1;
		}
	}
	if(request.getAttribute("books")!=null){
		books = (List<Book>)request.getAttribute("books");	
	}else{
		books = service.viewBooksBySearchString("", 1);
	}
    %>
<jsp:include page='include.html'></jsp:include>
<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="../admin">Back</a><br/>
		
<script>
function pageBooks(val){
	$.ajax({url: "pageBooks",data: { pageNo: val, searchString : $('#searchString').val()},
		})
		  .done(function( data ) {
		    $('#booksTable').html(data);
		  });
}
	
	function searchBooks(){
		$.ajax({url: "searchBooks", data: { searchString : $('#searchString').val()},
			})
			  .done(function( data ) {
			    $('#booksTable').html(data);
			  });
	}
</script>

<nav>
  <ul class="pagination">
    <%for(int i=1; i<=pageCount;i++){ %>
    <li><a id="pageNo" onclick="pageBooks(<%=i%>)"><%=i%></a></li>
	<%} %>
  </ul>
</nav>

<form action="searchBook" method="post">
	<input type="text" name="searchString" id="searchString" placeholder="Enter Author Name" onkeyup="searchBooks()"/>
</form>
	

<body>
	<h3>List of Books</h3>
	<table class="table table-hover" id="booksTable">
		<thead>
			<tr>
				<th>Book Title</th>
				<th>Author</th>
				<th>Genre</th>
				<th>Publisher</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
		</thead>
		<tbody>
		<% for(Book b : books) {%>
			<tr>
				<td><%=b.getTitle() %></td>
				<td><%=b.getAuthors().get(0).getAuthorName() %></td>
				<td><%=b.getGenres().get(0).getGenre_name() %></td>
				<td><%=b.getPublisher().getPublisherName() %></td>
				<td><button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#bookModal"
							href='editbook.jsp?bookId=<%=b.getBookId()%>'>EDIT</button></td>
				<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=b.getBookId()%>'">DELETE</button></td>
			</tr>
			<% } %>
		</tbody>
	</table>
	
	<div id="bookModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
</body>
</div>
</div>