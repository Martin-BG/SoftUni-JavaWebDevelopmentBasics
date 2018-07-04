<%@ page import="org.softuni.javaee.model.Product" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>All Products</h1>
<hr/>
<ol>
    <% Map<String, Product> products = (LinkedHashMap<String, Product>) application.getAttribute("products");%>
    <% for (String product : products.keySet()) { %>
    <li>
        <h3>
            <a href="${pageContext.request.contextPath}/products/details.jsp?name=<%=product%>">
                <%=product%>
            </a>
        </h3>
    </li>
    <%}%>
</ol>
</body>
</html>
