<%@ page import="org.softuni.simplejsp.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.softuni.simplejsp.entities.Type" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>
    <h1>All Products</h1>
    <hr />
    <% List<Product> productList = new ArrayList<Product>() {{
        add(new Product("Chushkopek", "A universal tool for...", Type.DOMESTIC));
        add(new Product("Injektoplqktor", "Dunno what this is...", Type.COSMETIC));
        add(new Product("Plumbus", "A domestic tool for everything...", Type.FOOD));
    }};%>
    <% for(Product product : productList) { %>
        <h3>
            <a href="/products/details.jsp?name=<%= product.getName() %>"><%= product.getName() %></a>
        </h3>
    <% } %>
</body>
</html>
