<%@ page import="fdmc.data.Cat" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<% String catName = request.getParameter("catName"); %>
<% Cat cat = ((Map<String, Cat>) application.getAttribute("cats")).get(catName); %>
<% if (cat != null) { %>
<h1>Cat - <%=cat.getName()%>
</h1>
<hr/>
<h3>Breed: <%= cat.getBreed()%>
</h3>
<h3>Color: <%= cat.getColor()%>
</h3>
<h3>Number of Legs: <%= cat.getNumberOfLegs()%>
</h3>
<% } else { %>
<h1>Cat, with name - <%=catName%> was not found.</h1>
<% } %>
<hr/>
<a href="<c:url value="/cats/all"/>">Back</a>
</body>
</html>
