package main.controllers;

import main.entities.Order;
import main.entities.Role;
import main.repositories.OrderRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;

import java.util.List;

@Controller
public class OrderController extends BaseController {
    private OrderRepository orderRepository;

    public OrderController() {
        this.orderRepository = new OrderRepository();
    }

    @GetMapping(route = "/orders/all")
    public String allOrders(HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        List<Order> allOrders = this.orderRepository.findAll();

        StringBuilder result = new StringBuilder();
        int counter = 1;

        for (Order order : allOrders) {
            result.append("<tr class=\"row\">");
            result.append("<th class=\"col-md-1\">" + counter + "</th>");
            result.append("<td class=\"col-md-4\">" + order.getId() + "</td>");
            result.append("<td class=\"col-md-2\">" + order.getClient().getUsername() + "</td>");
            result.append("<td class=\"col-md-2\">" + order.getProduct().getName() + "</td>");
            result.append("<td class=\"col-md-2\">" + order.getOrderedOn().toString() + "</td>");
            result.append("</tr>");
        }

        model.addAttribute("allOrders", result.toString());

        return super.view("orders-all");
    }
}
