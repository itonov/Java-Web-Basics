package org.servlets;

import org.data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catName = req.getParameter("catName");
        List<Cat> allCats = (List<Cat>) this.getServletContext().getAttribute("cats");

        Cat neededCat = null;

        for (Cat cat : allCats) {
            if (cat.getName().equals(catName)) {
                neededCat = cat;
            }
        }

        StringBuilder result = new StringBuilder();

        if (neededCat == null) {
            result.append(String.format("<h1>Cat, with name - %s was not found.</h1>", catName));
        } else {
            result.append(String.format("<h1> Cat - %s</h1>", neededCat.getName()));
            result.append("<hr>");
            result.append(String.format("<h2>Breed: %s</h2>", neededCat.getBreed()));
            result.append(String.format("<h2>Color: %s</h2>", neededCat.getColor()));
            result.append(String.format("<h2>Number of legs: %s </h2>", neededCat.getNumberOfLegs()));
            result.append("<hr>");
        }

        result.append("<a href=\"/cats/all\">Back</a>");

        PrintWriter out = resp.getWriter();
        out.println(result);
    }
}
