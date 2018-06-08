<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<h1>Login!</h1>
<br/>
<form method="post" action="<c:url value="/users/login"/>">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"/>
    <br/>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"/>
    <br/>
    <button type="submit">Login</button>
</form>
<hr/>
<a href="<c:url value="/"/>">Back to Home</a>
</body>
</html>
