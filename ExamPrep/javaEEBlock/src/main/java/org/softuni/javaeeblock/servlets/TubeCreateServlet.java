package org.softuni.javaeeblock.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tubes/create")
public class TubeCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder("<!DOCTYPE html>");
        result.append("<html lang=\"en\">");
        result.append("<body>");
        result.append("<h1>Create Tube</h1>");
        result.append("<hr/>");
        result.append("<form action=\"/tubes/create\" method=\"post\">");
        result.append("Title: <input type=\"text\" name=\"title\" placeholder=\"Title...\"><br/>");
        result.append("<hr/>");
        result.append("Description: <input type=\"text\" name=\"description\" placeholder=\"Description...\"><br/>");
        result.append("<hr/>");
        result.append("Video Link: <input type=\"text\" name=\"link\" placeholder=\"Video Link...\"><br/>");
        result.append("<hr/>");
        result.append("<button type=\"submit\" formmethod=\"post\">Submit</button><br/>");
        result.append("</form>");
        result.append("</body>");
        result.append("</html>");

        resp.addHeader("Content-Type", "text/html");

        PrintWriter out = resp.getWriter();
        out.println(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().setAttribute("title", req.getParameter("title"));
        this.getServletContext().setAttribute("description", req.getParameter("description"));
        this.getServletContext().setAttribute("link", req.getParameter("link"));

        resp.sendRedirect("/tubes/details");
    }
}
