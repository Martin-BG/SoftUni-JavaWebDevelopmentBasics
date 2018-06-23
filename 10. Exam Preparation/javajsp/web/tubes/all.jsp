<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Tubes</title>
</head>
<body>
<%
    List<String> tubes = new ArrayList<String>() {{
        add("Feel it Steel");
        add("Despacito");
        add("Gospodari Na Efira – ep. 25");
    }};
%>
<h1>All Tubes</h1>
<hr/>
<ol>
    <% for (String tubeTitle : tubes) { %>
    <li>
        <h3>
            <a href="${pageContext.request.contextPath}/tubes/details.jsp?title=<%=tubeTitle%>">
                <%=tubeTitle%>
            </a>
        </h3>
    </li>
    <%}%>
</ol>
</body>
</html>
