package org.servlets;

import org.data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cats/create")
public class CatCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder("<h1>Create a Cat!</h1>");
        result.append("<form method=\"post\">");
        result.append("<label for=\"name\">Name:</label>");
        result.append("<input type=\"text\" name=\"name\" id=\"name\">");
        result.append("</br>");
        result.append("<label for=\"breed\">Breed:</label>");
        result.append("<input type=\"text\" name=\"breed\" id=\"breed\">");
        result.append("</br>");
        result.append("<label for=\"color\">Color</label>");
        result.append("<input type=\"text\" name=\"color\" id=\"color\">");
        result.append("</br>");
        result.append("<label for=\"legs\">Number of legs</label>");
        result.append("<input type=\"number\" name=\"legs\" id=\"legs\">");
        result.append("</br>");
        result.append("<button type=\"submit\">Create Cat</button>");
        result.append("</form>");
        result.append("</br>");
        result.append("<a href=\"/\">Back To Home</a>");
        PrintWriter out = resp.getWriter();

        out.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String breed = req.getParameter("breed");
        String color = req.getParameter("color");
        int numberOfLegs = Integer.parseInt(req.getParameter("legs"));

        Cat newCat = new Cat(name, breed, color, numberOfLegs);

        List<Cat> cats = (List<Cat>) this.getServletContext().getAttribute("cats");

        if (cats == null) {
            cats = new ArrayList<>();
        }

        cats.add(newCat);

        this.getServletContext().setAttribute("cats", cats);

        resp.sendRedirect("/cats/profile?catName=" + newCat.getName());
    }
}
