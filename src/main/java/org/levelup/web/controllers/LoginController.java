package org.levelup.web.controllers;

import org.levelup.dao.UserDao;
import org.levelup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static org.levelup.web.AppConstants.*;

@Controller
public class LoginController {
    @Autowired
    UserDao dao;

    @GetMapping(path = LOGIN_PATH)
    public String loginPage(@RequestParam(required = false) String login) {
        return "login";
    }

    @PostMapping(path = LOGIN_PATH)
    public String doPost(HttpSession session,
                       @RequestParam(USER_NAME_PARAMETER) String login,
                       @RequestParam(PASSWORD_PARAMETER) String password) {
        if (session.getAttribute(USER_NAME_PARAMETER) != null) {
            return REDIRECT;
        }

        if (login == null || password == null) {
            return REDIRECT + LOGIN_PAGE;
        }
           User user = dao.findByLogin(login);

        if (user == null) {
            session.setAttribute("message", "user is null. login= " + login);
            return REDIRECT + LOGIN_PAGE;
        }

        if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
            session.setAttribute(VERIFIED_USER_NAME_ATTRIBUTE, login);
            return REDIRECT;
        } else {
            session.setAttribute("message", "wrong login or password");
            return REDIRECT + LOGIN_PAGE;
        }
    }
}