package org.softuni.simpleservlets.servlets;

import org.softuni.simpleservlets.entities.Product;
import org.softuni.simpleservlets.entities.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products/create")
public class ProductCreateServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        this.getServletContext().setAttribute("products", new ArrayList<>());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder("<!DOCTYPE html>");
        result.append("<html lang=\"en\">");
        result.append("<body>");
        result.append("<form action=\"/products/create\" method=\"post\">");
        result.append("<input type=\"text\" name=\"name\" placeholder=\"Name...\"/><br/>");
        result.append("<input type=\"text\" name=\"description\" placeholder=\"Description...\"/><br/>");
        result.append("<select name=\"type\">");
        result.append("<option value=\"FOOD\">Food</option>");
        result.append("<option value=\"DOMESTIC\">Domestic</option>");
        result.append("<option value=\"HEALTH\">Health</option>");
        result.append("<option value=\"COSMETIC\">Cosmetic</option>");
        result.append("<option value=\"OTHER\">Other</option>");
        result.append("</select><br/>");
        result.append("<button class=\"button\" type=\"submit\">Submit</button>");
        result.append("</form>");
        result.append("</body>");
        result.append("</html>");

        resp.addHeader("Content-type", "text/html");

        PrintWriter writer = resp.getWriter();
        writer.println(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product newProduct = new Product();

        newProduct.setName(req.getParameter("name"));
        newProduct.setDescription(req.getParameter("description"));
        newProduct.setProductType(Type.valueOf(req.getParameter("type")));

        List<Product> productList = (List<Product>) this.getServletContext().getAttribute("products");
        productList.add(newProduct);

        this.getServletContext().setAttribute("products", productList);

        resp.sendRedirect("/products/all");
    }
}
