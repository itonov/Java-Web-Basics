package org.softuni.fdmc.servlets.orders;

import org.softuni.fdmc.data.models.UserRole;
import org.softuni.fdmc.data.repos.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders/all")
public class OrdersAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (((UserRepository) this.getServletContext()
                .getAttribute("users"))
                .getByUsername(req.getSession()
                        .getAttribute("username")
                        .toString())
                        .getRole()
                .compareTo(UserRole.USER) == 0) {
            resp.sendError(403, "Only Admins can review orders!");
            return;
        }

        req.getRequestDispatcher("all.jsp").forward(req, resp);
    }
}
