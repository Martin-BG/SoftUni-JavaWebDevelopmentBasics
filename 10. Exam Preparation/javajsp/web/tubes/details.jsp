<%@ page import="org.softuni.javaee.model.Tube" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Tubes</title>
</head>
<body>
<%
    Map<String, Tube> tubes = new LinkedHashMap<String, Tube>() {{
        put("Feel it Steel",
                new Tube("Feel it Steel", "Some cool new Song…", 5, "Parkash"));
        put("Despacito",
                new Tube("Despacito", "No words … Just … No!", 250, "Stamat"));
        put("Gospodari Na Efira – ep. 25",
                new Tube("Gospodari Na Efira – ep. 25", "Mnogo smqh imashe tuka…", 3, "Trichko"));
    }};

    Tube selectedTube = tubes.get(URLDecoder.decode(request.getParameter("title"), "UTF-8"));
%>

<h1>
    Tube - <%=selectedTube.getTitle()%>
</h1>
<hr/>

<h2>Description</h2>
<h3>
    <%=selectedTube.getDescription()%>
</h3>
<hr/>

<h2>Views</h2>
<h3>
    <%=selectedTube.getViews()%>
</h3>
<hr/>

<h2>Uploader</h2>
<h3>
    <%=selectedTube.getUploader()%>
</h3>
</body>
</html>
