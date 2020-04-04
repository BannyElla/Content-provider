package org.levelup.web.controllers;

import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Role;
import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.levelup.web.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static org.levelup.web.AppConstants.*;

@Controller
public class RegistrationController {
    @Autowired
    UserDao users;
    @Autowired
    RoleDao roles;

    @GetMapping(path = "/registration")
    public String registrationPage(ModelMap model) {
        RegistrationForm form = new RegistrationForm();
        form.setLogin("");
        form.setPassword("");
        model.addAttribute("form", form);
        return REGISTRATION;
    }

    @PostMapping(path = "/registration")
    public String processRegistration(HttpSession session,
                                      @RequestParam(USER_NAME_PARAMETER) String login,
                                      @RequestParam(PASSWORD_PARAMETER) String password) {

        Role userRole = roles.findByName(UserRole.USER);
        User user = users.create(login, password, userRole);

        session.setAttribute("message", "registration id = " + user.getId());
        return REDIRECT + LOGIN_PAGE + login;
    }

}
