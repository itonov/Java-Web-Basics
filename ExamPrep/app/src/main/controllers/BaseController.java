package main.controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.BindingResult;
import org.softuni.summermvc.api.Model;

public abstract class BaseController {

    private boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession().getAttributes().containsKey("user-id");
    }

    protected String view(HttpSoletRequest request, Model model, String view) {
        return "template:" + view;
    }

    protected String view(HttpSoletRequest request, Model model, String view, BindingResult bindingResult) {
        StringBuilder errorsResult = new StringBuilder();

        for (String error : bindingResult.getErrors()) {
            errorsResult.append("<li>" + error + "</li>");
        }

        model.addAttribute("errors", errorsResult.toString());

        return "template:" + view;
    }

    protected String redirect(HttpSoletRequest request, Model model, String view) {
        return "redirect:/" + view;
    }
}
