<%@ page import="org.softuni.javaee.models.Product" %>
<%@ page import="org.softuni.javaee.repositories.ProductRepository" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<%
    ProductRepository products = (ProductRepository) application.getAttribute("products");
    Product selectedProduct = products.getByName(URLDecoder.decode(request.getParameter("name"), "UTF-8"));
%>

<h1>
    Product - <%=selectedProduct.getName()%>
</h1>
<hr/>

<h2>Description</h2>
<h3>
    <%=selectedProduct.getDescription()%>
</h3>
<hr/>

<h2>Type</h2>
<h3>
    <%=selectedProduct.getType()%>
</h3>
<hr/>

<a href="all">Back to all products</a>
</body>
</html>
