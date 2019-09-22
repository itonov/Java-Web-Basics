<%@ page import="java.util.ArrayList" %>
<%@ page import="org.softuni.simplejsp.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.softuni.simplejsp.entities.Type" %>
<%@ page import="java.net.URLDecoder" %>
<html>
<head>
    <title>Tubes</title>
</head>
<body>
    <% List<Product> productList = new ArrayList<Product>() {{
        add(new Product("Chushkopek", "A universal tool for...", Type.DOMESTIC));
        add(new Product("Injektoplqktor", "Dunno what this is...", Type.COSMETIC));
        add(new Product("Plumbus", "A domestic tool for everything...", Type.FOOD));
    }};%>

    <% String productName = URLDecoder.decode(request.getParameter("name"), "UTF-8"); %>
    <% Product neededProduct = productList.stream().filter(product -> product.getName().equals(productName)).findFirst().orElse(null); %>
    Name: <%= neededProduct.getName() %>
    <br/>
    Description: <%= neededProduct.getDescription() %>
    <br/>
    Type: <%= neededProduct.getProductType().toString().charAt(0)
            + neededProduct.getProductType().toString().substring(1).toLowerCase() %>
    <br/>
<a href="/products/all.jsp">Back</a>
</body>
</html>
