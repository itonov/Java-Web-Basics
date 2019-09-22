package org.softuni.javaeeblock.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tubes/details")
public class TubeDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();
        result.append(String.format("<h1>Successfully created Tube - %s</h1><br/>",
                this.getServletContext().getAttribute("title")));
        result.append("<hr/>");
        result.append("<h3>Description:</h3><br/>");
        result.append(this.getServletContext().getAttribute("description"));
        result.append("<br/><hr/>");
        result.append("<h3>Video Link</h3><br/>");
        result.append(this.getServletContext().getAttribute("link"));

        PrintWriter out = resp.getWriter();
        out.println(result.toString());
    }
}
