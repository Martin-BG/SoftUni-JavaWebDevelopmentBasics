<%@ page import="fdmc.data.models.Cat" %>
<%@ page import="fdmc.data.repositories.CatRepository" %>
<jsp:useBean id="urlProfile" class="java.lang.String"/>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/ico/favicon.ico" />">
</head>
<body>
<h1>All Cats</h1>
<hr/>
<% CatRepository cats = (CatRepository) application.getAttribute("cats");%>
<% if (cats.getAllCats().isEmpty()) { %>
<h3>
    There are no cats. <a href="<c:url value="/cats/create"/>">Create Some!</a>
</h3>
<% } else { %>
<% for (Cat cat : cats.getAllCats()) { %>
<h3>
    <% urlProfile = "/cats/profile?catName=" + cat.getName(); %>
    <a href="<%=urlProfile %>"><%=cat.getName()%>
    </a>
</h3>
<% }%>
<%} %>

<hr/>
<a href="<c:url value="/"/>">Back to Home</a>
</body>
</html>
