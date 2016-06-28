<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.AdministrativeService" %>
    <%@ page import="com.gcit.lms.domain.LibraryBranch" %>
    <%AdministrativeService service = new AdministrativeService(); 
    List<LibraryBranch> libs = new ArrayList<LibraryBranch>();
    libs = service.viewLibraryBranch();
    Integer branchId = Integer.parseInt(request.getParameter("branchId"));
    %>

<jsp:include page='include.html'></jsp:include>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Admin! Choose your action<p>
	</div>
</div>

<body>

<div class="users">
    <div class="container">
<a href="viewbranches.jsp" style="font-size:20px;">Back</a><br/><br/>

<a href="showbooks.jsp?branchId=<%=branchId%>">Show Books in Branch</a></br>
<a href="addbook.jsp?branchId=<%=branchId%>">Add Book to Branch</a></br>
<a href="addbook.jsp?branchId=<%=branchId%>">Add Borrower to Branch</a>

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