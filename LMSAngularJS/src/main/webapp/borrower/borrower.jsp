<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page='include.html'></jsp:include>'
<div class="container">
 	<div class="jumbotron">
		<h2>GCIT Library Management System</h2>
		<p>Hello Borrower! Enter your Card Number<p>
	</div>
</div>

<body>
	<div class="users">
	<div class="container">
		<a style="font-size:20px;" href="borrower">Back</a><br/>
${message}
		<h3 class="title">Borrower</h3>
	<form role="form" class="form-horizontal" action="checkCardNo" method="post">
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="borrower">Card Number:</label>
	      <div class="col-sm-10">
	      	<input type="text" class="form-control" name="cardNo" placeholder="Enter Card Number">
		  </div>
	    </div>
	    
		<div class="form-group">
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-primary">Submit</button>
	      </div>
	    </div>
	</form><br/>
		
		
	</div>
	</div>
</body>