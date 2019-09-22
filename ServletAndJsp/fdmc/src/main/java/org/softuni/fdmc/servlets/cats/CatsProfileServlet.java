package org.softuni.fdmc.servlets.cats;

import org.softuni.fdmc.data.repos.CatRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/profile")
public class CatsProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int updatedViews = ((CatRepository) this.getServletContext()
                .getAttribute("cats"))
                .getByName(req.getParameter("catName"))
                .getViews()
                + 1;

        ((CatRepository) this.getServletContext()
                .getAttribute("cats"))
                .getByName(req.getParameter("catName"))
                .setViews(updatedViews);

        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println();
    }
}
