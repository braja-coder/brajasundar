<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Covid19 Kit-Home</title>
</head>
<body>
<div>
<jsp:include page="header.jsp"/>
<hr/>
<h4 style="text-align:center; background-color: green" > Login As Admin</h4>

	
	<form action="admin?action=loginadmin" method="post">
	
			<label>Enter admin Id : </label> 
			<input type="text" name="adminid">
		
			<label>Enter admin password : </label> 
			<input type="text" name="password">
			<button> Login </button>
	</form>
<h4 style="text-align:center; background-color: green" > Login As User</h4>	
	<form action="user?action=loginuser" method="post">
	
			<label>Enter user Id : </label> 
			<input type="text" name="userid">
		
			<label>Enter user password : </label> 
			<input type="text" name="password">
			<button> Login </button>
	</form>
</div>
<hr/>
<br/>

<c:choose>
	<c:when test="${msg == null }">
	<p> Welcome All!! </p>
	</c:when>
	<c:otherwise>
	<p> <strong>${msg }</em></strong></p>
	</c:otherwise>
	</c:choose>
	
<div>
<label>Create Cart As User   </label>
	<a href="user?action=newuser"><button>Create Corona Kit</button></a>
</div>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>