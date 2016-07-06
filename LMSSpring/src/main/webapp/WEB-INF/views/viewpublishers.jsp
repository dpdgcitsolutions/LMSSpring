<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.Publisher" %>
    <%
    AdministrativeService aService = (AdministrativeService)request.getAttribute("service");
    List<Publisher> pubs = new ArrayList<Publisher>();
    pubs = aService.viewPublishers();%>
<%@ include file="include.html" %>
<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="admin">Back</a><br/>
	
<body>
<h3>List of Publishers</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>Address</th>
				<th>Phone Number</th>
				<th>Edit Publisher</th>
				<th>Delete Publisher</th>
			</tr>
		</thead>
		<tbody>
		<% for(Publisher p : pubs) {%>
			<tr>
				<td><%=p.getPublisherName() %></td>
				<td><%=p.getPublisherAddress() %></td>
				<td><%=p.getPublisherPhone() %></td>
				<td><button type="button" class="btn btn-warning" onclick="javascript:location.href='editAuthor?authorId=<%=p.getPublisherId()%>'">EDIT</button></td>
				<td><button type="button" class="btn btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=p.getPublisherId()%>'">DELETE</button></td>
			</tr>
			<% } %>
		</tbody>
	</table>

</body>
</div>
</div>