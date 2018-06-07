<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<h1>Register!</h1>
<br/>
<form method="post" action="<c:url value="/users/register"/>">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"/>
    <br/>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"/>
    <br/>
    <label for="confirmPassword">Confirm Password:</label>
    <input type="password" id="confirmPassword" name="confirmPassword"/>
    <br/>
    <label for="isAdmin">Administrator:</label>
    <input type="checkbox" id="isAdmin" name="isAdmin" value="isAdmin" checked>
    <br/>
    <button type="submit">Register</button>
</form>
<hr/>
<a href="<c:url value="/"/>">Back to Home</a>
</body>
</html>
