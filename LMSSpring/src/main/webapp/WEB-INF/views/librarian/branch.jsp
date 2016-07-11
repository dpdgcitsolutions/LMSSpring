<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.LibrarianService" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch" %>
    <%
    LibrarianService service = (LibrarianService)request.getAttribute("service");
    List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
    libs = service.viewLibraryBranch();
    Integer branchId = Integer.parseInt(request.getParameter("branchId"));
    %>

<%@ include file="include.html" %>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Admin! Choose your branch<p>
	</div>
</div>

<body>

<div class="users">
    <div class="container">
<a href="../librarian" style="font-size:20px;">Back</a><br/><br/>

<a href="showBooks?branchId=<%=branchId%>">Show Books in Branch</a></br>
<a href="addBook?branchId=<%=branchId%>">Add Book to Branch</a></br>
<a href="showBorrowers?branchId=<%=branchId%>">Show Borrowers</a>

	</div>
</div>

<div id="editBranchModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>

</body>
</html>