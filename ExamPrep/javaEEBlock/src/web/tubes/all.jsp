<%@ page import="org.softuni.javaeeblock.entities.Tube" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>
    <h1>All Tubes</h1>
    <hr />
    <% List<Tube> tubes =  new ArrayList<Tube>() {{
        add(new Tube("Feel it Steel", "Some cool new Song...", 5, "Prakash"));
        add(new Tube("Despacito", "No words ... Just ... No!", 250, "Stamat"));
        add(new Tube("Gospodari Na Efira - ep. 25", "Mnogo smqh imashe tuka...", 3, "Trichko"));
    }}; %>

    <% for(Tube tube : tubes) { %>
        <h3>
            <a href="/tubes/details.jsp?title=<%= tube.getTitle() %>"><%= tube.getTitle() %></a>
        </h3>
    <% } %>
</body>
</html>
