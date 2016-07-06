<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Genre" %>
    <%
    AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
    List<Genre> genres = new ArrayList<Genre>();
    genres = aService.viewGenres();%>
    
    
<%@ include file="include.html" %>
<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="admin">Back</a><br/>
	
<body>
<h3>List of Genres</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>Edit Publisher</th>
				<th>Delete Publisher</th>
			</tr>
		</thead>
		<tbody>
		<% for(Genre g : genres) {%>
			<tr>
				<td><%=g.getGenre_name() %></td>
				<td><button type="button" class="btn btn-warning" onclick="javascript:location.href='editAuthor?authorId=<%=g.getGenre_id()%>'">EDIT</button></td>
				<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=g.getGenre_id()%>'">DELETE</button></td>
			</tr>
			<% } %>
		</tbody>
	</table>

</body>
</html>

</div>
</div>