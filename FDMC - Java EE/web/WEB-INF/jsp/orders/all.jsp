<%@ page import="fdmc.data.models.Order" %>
<%@ page import="fdmc.data.repositories.OrderRepository" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
<h1>All Orders</h1>
<hr/>

<% OrderRepository orders = (OrderRepository) application.getAttribute("orders");%>
<% for (Order order : orders.allOrders()) { %>
<h4>Cat - <%= order.getCat().getName()%>
</h4>
<h4>Client: <%= order.getClient().getUsername()%>
</h4>
<h4>Made on: <%= order.getMadeOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))%>
</h4>
<hr/>
<%} %>

<a href="<c:url value="/"/>">Back to Home</a>
</body>
</html>
