<%@ page import="org.softuni.javaee.model.Product" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<%
    Map<String, Product> products = (LinkedHashMap<String, Product>) application.getAttribute("products");

    Product selectedProduct = products.get(URLDecoder.decode(request.getParameter("name"), "UTF-8"));
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

<a href="all.jsp">Back to all products</a>
</body>
</html>
