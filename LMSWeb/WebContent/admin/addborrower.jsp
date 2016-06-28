<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page='include.html'></jsp:include>
<body>
	<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="../admin">Back</a><br/>
		
		<h3 class="title">Borrower</h3>
	<form role="form" class="form-horizontal" action="addBorrower" method="post">
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="borrower">Name:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="borrowerName" placeholder="Enter Borrower Name">
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="borrower">Address:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="borrowerAddress" placeholder="Enter Borrower Address">
		  </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="borrower">Phone:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="borrowerPhone" placeholder="Enter Borrower Phone">
		  </div>
	    </div>
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Add Borrower</button>
	      </div>
	    </div>
	</form><br/>
		
		
	</div>
	</div>
</body>