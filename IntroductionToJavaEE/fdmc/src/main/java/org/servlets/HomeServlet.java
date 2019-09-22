package org.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        StringBuilder respPage = new StringBuilder("<h1>Welcome to Fluffy Duffy Munchkin Cats!</h1>");
        respPage.append("</br>");
        respPage.append("<h3>Navigate through the application using the links below!</h3>");
        respPage.append("<hr>");
        respPage.append("<a href=\"/cats/create\">Create Cat</a>");
        respPage.append("</br>");
        respPage.append("<a href=\"/cats/all\">All Cats</a>");

        out.println(respPage);
    }
}
