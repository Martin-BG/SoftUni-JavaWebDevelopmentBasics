<%@ page import="org.softuni.javaee.models.Product" %>
<%@ page import="org.softuni.javaee.repositories.ProductRepository" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>All Products</h1>
<hr/>
<ol>
    <% ProductRepository products = (ProductRepository) application.getAttribute("products");%>
    <% for (Product product : products.getAll()) { %>
    <li>
        <h3>
            <a href="${pageContext.request.contextPath}/products/details?name=<%=product.getName()%>">
                <%=product.getName()%>
            </a>
        </h3>
    </li>
    <%}%>
</ol>
<hr/>
<a href="create">Create a new product</a>
</body>
</html>
