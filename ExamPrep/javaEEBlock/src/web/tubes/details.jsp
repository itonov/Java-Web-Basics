<%@ page import="org.softuni.javaeeblock.entities.Tube" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tubes</title>
</head>
<body>
    <% List<Tube> tubes =  new ArrayList<Tube>() {{
        add(new Tube("Feel it Steel", "Some cool new Song...", 5, "Prakash"));
        add(new Tube("Despacito", "No words ... Just ... No!", 250, "Stamat"));
        add(new Tube("Gospodari Na Efira - ep. 25", "Mnogo smqh imashe tuka...", 3, "Trichko"));
    }}; %>

    <% String tubeTitle = URLDecoder.decode(request.getParameter("title"), "UTF-8"); %>
    <% Tube neededTube = tubes.stream().filter(tube -> tube.getTitle().equals(tubeTitle)).findFirst().orElse(null); %>
    Title: <%= neededTube.getTitle() %>
    <br/>
    Description: <%= neededTube.getDescription() %>
    <br/>
    Views: <%= neededTube.getViews() %>
    <br/>
    Uploader: <%= neededTube.getUploader() %>
    <br/>
<a href="/tubes/all.jsp">Back</a>
</body>
</html>
