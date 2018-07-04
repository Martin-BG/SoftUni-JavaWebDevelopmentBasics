<%@ page import="org.softuni.javaee.models.ProductType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<%
    final List<String> types = ProductType.getAsList();
%>

<h1>Create a Product</h1>
<br/>
<form method="post" action="create">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"/>
    <br/><br/>
    <label for="description">Description:</label>
    <input type="text" id="description" name="description"/>
    <br/><br/>
    <label for="type">Type:</label>
    <input list="types" id="type" name="type"/>
    <datalist id="types">
        <% for (String type : types) { %>
        <option value="<%=type%>">
                <%}%>
    </datalist>
    <br/><br/>
    <button type="submit">Create Product</button>
</form>
<hr/>
<a href="all">Back to all products</a>
</body>
</html>
