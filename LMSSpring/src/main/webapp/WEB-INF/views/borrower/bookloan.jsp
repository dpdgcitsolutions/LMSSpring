<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gcit.lms.service.BorrowerService" %>
    <%@ page import="com.gcit.lms.domain.Borrower" %>
    <%BorrowerService service = new BorrowerService(); 
    int cardNo = (int)request.getAttribute("cardNo");
    %>

<jsp:include page='include.html'></jsp:include>
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Borrower! Choose your action<p>
	</div>
</div>

<body>

<div class="users">
    <div class="container">
<a href="../borrower" style="font-size:20px;">Back</a><br/><br/>
	<form role="form" class="form-horizontal" action="checkOut" method="post">
		<input type="hidden" name="cardNo" value=<%=cardNo%>>
	    
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Check Out a Book</button>
	      </div>
	    </div>
	</form>
	
	<form role="form" class="form-horizontal" action="checkIn" method="post">
		<input type="hidden" name="cardNo" value=<%=cardNo%>>
	    
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	      
	        <button type="submit" class="btn btn-primary">Return a Book</button>
	      </div>
	    </div>
	</form>
	<br/>
	</div>
</div>

</body>
</html>