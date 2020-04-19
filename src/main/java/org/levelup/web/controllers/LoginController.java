package org.levelup.web.controllers;

import org.levelup.dao.UserDao;
import org.levelup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static org.levelup.web.AppConstants.*;

@Controller
public class LoginController {
    @Autowired
    UserDao dao;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping(path = LOGIN_PAGE)
    public String loginPage(HttpSession session,@RequestParam(required = false) String login) {
        if (session.getAttribute(VERIFIED_USER_NAME_ATTRIBUTE) != null) {
            return REDIRECT;
        }
        return LOGIN;
    }

    //@PostMapping(path = LOGIN_ACTION) //отключили обработки форуму логина, чтобы это делал спринговый LoginController
    public String doPost(HttpSession session,
                       @RequestParam(USER_NAME_PARAMETER) String login,
                       @RequestParam(PASSWORD_PARAMETER) String password) {
        if (session.getAttribute(VERIFIED_USER_NAME_ATTRIBUTE) != null) {
            return REDIRECT;
        }

        if (login == null || password == null) {
            return REDIRECT + LOGIN_PAGE_WITH_PARAMETER;
        }
           User user = dao.findByLogin(login);

        if (user == null) {
            session.setAttribute("message", "wrong login or password");
            return REDIRECT + LOGIN_PAGE_WITH_PARAMETER;
        }

        if (user != null && encoder.matches(password, user.getPassword())) {
            session.setAttribute(VERIFIED_USER_NAME_ATTRIBUTE, login);
            return REDIRECT;
        } else {
            session.setAttribute("message", "wrong login or password");
            return REDIRECT + LOGIN_PAGE_WITH_PARAMETER;
        }
    }
}
