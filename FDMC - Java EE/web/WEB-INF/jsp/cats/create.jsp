<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<h1>Create a Cat!</h1>
<br/>
<form method="post" action="<c:url value="/cats/create"/>">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"/>
    <br/>
    <label for="breed">Breed:</label>
    <input type="text" id="breed" name="breed"/>
    <br/>
    <label for="color">Color:</label>
    <input type="text" id="color" name="color"/>
    <br/>
    <label for="legs">Number of legs:</label>
    <input type="number" id="legs" name="legs"/>
    <br/>
    <button type="submit">Create Cat</button>
</form>
<hr/>
<a href="<c:url value="/"/>">Back to Home</a>
</body>
</html>
