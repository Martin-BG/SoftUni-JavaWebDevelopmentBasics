package controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.BindingResult;
import org.softuni.summermvc.api.Model;

public class BaseController {

    protected boolean isLoggedIn(HttpSoletRequest request) { // NOTE: isLoggedIn private -> protected
        return request.getSession() != null  //NOTE: added session != null check to isLoggedIn
                && request.getSession().getAttributes().containsKey("user-id");
    }

    protected String view(HttpSoletRequest request, Model model, String view) {
        return "template:" + view;
    }

    protected String error(Model model, BindingResult bindingResult) { //NOTE - implemented BindingResult (errors) class
        final StringBuilder errors = new StringBuilder();

        for (final String error : bindingResult.getErrors()) {
            errors.append("<li>")
                    .append(error)
                    .append("</li>");
        }
        model.addAttribute("errors", errors.toString());
        return "template:error";
    }

    protected String redirect(HttpSoletRequest request, Model model, String view) {
        return "redirect:/" + view;
    }
}
