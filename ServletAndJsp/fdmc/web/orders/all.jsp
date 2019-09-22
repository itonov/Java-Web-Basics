<%@ page import="org.softuni.fdmc.data.models.Order" %>
<%@ page import="org.softuni.fdmc.data.repos.OrderRepository" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 08-Jun-18
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FDMC</title>
</head>
<body>
    <h1>All Orders</h1>
    <hr/>
    <% Set<Order> allOrders = ((OrderRepository) application.getAttribute("orders")).getAll(); %>
    <% if (!allOrders.isEmpty()) { %>
        <% for (Order order : allOrders) { %>
            Client username: <%= order.getClient().getUsername() %>
            <br/>
            Cat name: <%= order.getCat().getName() %>
            <br/>
            Creation Date: <%= order.getMadeOn() %>
            <br/>
            <br/>
        <% } %>
    <% } else { %>
        There aren't any orders!
    <% } %>
</body>
</html>
