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

@WebServlet("/products/all")
public class ProductAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();

        List<Product> allProducts = (List<Product>) this.getServletContext().getAttribute("products");

        for (Product currentProduct : allProducts) {
            result.append("<a href=\"/products/details?name="
                    + currentProduct.getName()
                    + "\">"
                    + currentProduct.getName()
                    + "</a><br/>");
        }

        PrintWriter writer = resp.getWriter();
        writer.println(result.toString());
    }
}
