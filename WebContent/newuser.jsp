<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Covid19 Kit-New User(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
	<a href="showproductstoadd.jsp"><button style="float: left">Show All Products</button></a>
<hr/>
	<h2 style="text-align:center;">User Details</h2>
	
	<form action="user?action=insertuser" method="post">
		<div>
			<div><label for="pname">Enter User Name</label> </div>
			<div><input type="text" id="pname" name="pname"> </div>
		</div>
		<div>
			<div><label for="pemail">Enter Email ID</label> </div>
			<div><input type="email" id="pemail" name="pemail"> </div>
		</div>
		<div>
			<div><label for="pphone">Enter Phone Number</label> </div>
			<div><input type="text" id="pphone" name="pphone"> </div>
		</div>
		<br/> 
		<div>
			<div>
			<input type="submit" value="Submit"> 
			</div>
		</div>
	</form>
	

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>