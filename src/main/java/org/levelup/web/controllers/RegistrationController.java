package org.levelup.web.controllers;

import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.levelup.web.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.levelup.web.AppConstants.*;

@Controller
public class RegistrationController {
    @Autowired
    UserDao users;
    @Autowired
    RoleDao roles;

    @ModelAttribute("form")
    public RegistrationForm createForm() {
        RegistrationForm form = new RegistrationForm();
        form.setLogin("");
        form.setPassword("");
        return form;
    }

    @GetMapping(path = REGISTRATION_PATH)
    public String registrationPage(ModelMap model,  @ModelAttribute("form") RegistrationForm form) {
        model.addAttribute("form", createForm());
        return REGISTRATION;
    }

    @PostMapping(path = REGISTRATION_PATH)
    public String processRegistration(ModelMap model,
                                      @Validated
                                      @ModelAttribute("form") RegistrationForm form,
                                      BindingResult validationResult) {
        model.addAttribute("form", createForm());
        Role userRole = roles.findByName(UserRole.USER);
        try {
            users.create(form.getLogin(), form.getPassword(), userRole);
        } catch (Exception e) {
            validationResult.addError(
                    new FieldError("form", "login",
                            "User with login " + form.getLogin()
                                    + " is already registered."));
            return REGISTRATION;
        }
        return REDIRECT + LOGIN_PAGE + form.getLogin();
    }

}
