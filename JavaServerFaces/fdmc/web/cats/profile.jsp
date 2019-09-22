<%@ page import="org.softuni.fdmc.data.models.Cat" %>
<%@ page import="org.softuni.fdmc.data.models.Order" %>
<%@ page import="org.softuni.fdmc.data.models.User" %>
<%@ page import="org.softuni.fdmc.data.repos.CatRepository" %>
<%@ page import="org.softuni.fdmc.data.repos.OrderRepository" %>
<%@ page import="org.softuni.fdmc.data.repos.UserRepository" %>
<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: atriu
  Date: 1.6.2018 г.
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FDMC</title>
</head>
<body>
    <% String catName = request.getParameter("catName"); %>
    <% Cat cat = ((CatRepository) application.getAttribute("cats")).getByName(catName); %>
    <% if(cat != null) { %>
    <h1>Cat - <%=cat.getName()%></h1>
    <hr />
    <h3>Breed: <%= cat.getBreed()%></h3>
    <h3>Color: <%= cat.getColor()%></h3>
    <h3>Number Of Legs: <%= cat.getNumberOfLegs()%></h3>
    <h3>Views: <%=cat.getViews()%></h3>
    <h3>Creator: <%= cat.getCreator().getUsername()%></h3>
    <hr />
    <% } else { %>
    <h1>Cat, with name <%=catName%> was not found.</h1>
    <% } %>

<br />
    <button onclick="<% User buyer = ((UserRepository) application.getAttribute("users"))
        .getByUsername(session.getAttribute("username").toString());
    ((OrderRepository) application.getAttribute("orders"))
        .addOrder(new Order(buyer, cat, LocalDateTime.now()));%>">Order</button>
    <br />
<a href="/cats/all">Back</a>
</body>
</html>
