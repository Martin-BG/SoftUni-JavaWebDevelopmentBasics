<%@ page import="fdmc.util.LoggedUser" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<h1>Welcome to Fluffy Duffy Munchkin Cats!</h1>
<%= !LoggedUser.isPresent(request)
        ? "<h3>Login if you have an account, or Register if you don't!</h3>"
        : "<h3>Navigate through the application using the links below!</h3>"
%>
<hr/>
<% if (!LoggedUser.isPresent(request)) { %>
<a href="<c:url value="/users/login"/>">Login</a>
<br/>
<a href="<c:url value="/users/register"/>">Register</a>
<% } else { %>
<% if (LoggedUser.isAdmin(request)) { %>
<a href="<c:url value="/cats/create"/>">Create Cat</a>
<br/>
<a href="<c:url value="/orders/all"/>">All Orders</a>
<br/>
<% } %>
<a href="<c:url value="/cats/all"/>">All Cats</a>
<br/>
<a href="<c:url value="/users/logout"/>">Logout</a>
<% } %>
</body>
</html>
