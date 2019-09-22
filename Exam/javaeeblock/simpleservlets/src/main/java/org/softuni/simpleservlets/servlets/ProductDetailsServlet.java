package org.softuni.simpleservlets.servlets;

import org.softuni.simpleservlets.entities.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products/details")
public class ProductDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> allProducts = (List<Product>) this.getServletContext().getAttribute("products");

        String neededProductName = req.getParameter("name");

        Product neededProduct = allProducts
                .stream()
                .filter(product -> product.getName().equals(neededProductName))
                .findFirst()
                .orElse(null);

        StringBuilder result = new StringBuilder();
        result.append("Product name: " + neededProduct.getName() + "<br/>");
        result.append("Product description: " + neededProduct.getDescription() + "<br/>");
        result.append("Product type: "
                + (neededProduct.getProductType().toString().charAt(0)
                + neededProduct.getProductType().toString().substring(1).toLowerCase()) + "<br/>");

        resp.addHeader("Content-Type", "text/html");

        PrintWriter writer = resp.getWriter();
        writer.println(result.toString());
    }
}
