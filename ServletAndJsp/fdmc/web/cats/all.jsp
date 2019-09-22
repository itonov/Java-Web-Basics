<%@ page import="java.util.List" %>
<%@ page import="org.softuni.fdmc.data.models.Cat" %>
<%@ page import="org.softuni.fdmc.data.repos.CatRepository" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %><%--
  Created by IntelliJ IDEA.
  User: atriu
  Date: 1.6.2018 г.
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FDMC</title>
</head>
<body>
    <h1>All Cats</h1>
    <hr />
    <% CatRepository cats = (CatRepository) application.getAttribute("cats"); %>
    <% for(Cat cat : cats.getAllCats().stream().sorted(Comparator.comparing(Cat::getViews).reversed())
            .collect(Collectors.toList())) { %>
        <h3>
            <a href="/cats/profile?catName=<%= cat.getName()%>"><%= cat.getName()%></a>
        </h3>
    <% } %>

    <br />
    <a href="/">Back to Home</a>
</body>
</html>
