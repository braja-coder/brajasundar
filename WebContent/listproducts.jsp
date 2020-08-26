<%@page import="com.iiht.evaluation.coronakit.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Covid19 Kit-All Products(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<h2 style="text-align:center;">Admin Product Cart</h2>
<br/>

    <a href="admin?action=logout"><button style="float: right">Logout</button></a>
    <br/>
	<a href=newproduct.jsp><button style="float: left">Add New Product</button></a>
	<br/>
<br/>
	<%
		// fetch the shared data
		List<ProductMaster> products =  (List<ProductMaster>) request.getAttribute("products");
	%>
	<table border="1" width="100%">
		<tr>
			<th>Product Name</th>
			<th>Product Cost</th>
			<th>Product Description</th>
			<th></th>
			<th></th>
		</tr>
		<tbody>
			<% for(ProductMaster product : products) { %>
			<tr>
				<td><%=product.getProductName()%></td>
				<td><%=product.getCost()%></td>
				<td><%=product.getProductDescription()%></td>
				<td><a href="admin?action=editproduct&id=<%=product.getId()%>"><button>Edit</button></a></td>
				<td><a href="admin?action=deleteproduct&id=<%=product.getId()%>"><button>Delete</button></a></td>
			</tr>
			<% } %>
		</tbody>
	</table>

<hr/>	

</body>
</html>