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

@WebServlet("/cats/all")
public class AllCatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cat> allCats = (List<Cat>) this.getServletContext().getAttribute("cats");
        StringBuilder result = new StringBuilder("<h1>All Cats</h1>");
        result.append("<hr>");

        if (allCats == null) {
            result.append("<h2> There are no cats.");
            result.append("<a href=\"/cats/create\">Create some!</a>");
            result.append("</h2>");
        } else {
            for (Cat cat : allCats) {
                result.append(String.format("<a href=\"/cats/profile?catName=%s\">%s</a>", cat.getName(), cat.getName()));
                result.append("</br>");
            }
        }

        result.append("<a href=\"/\">Back To Home</a>");

        PrintWriter out = resp.getWriter();
        out.println(result);
    }
}
