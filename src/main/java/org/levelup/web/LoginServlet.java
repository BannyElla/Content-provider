package org.levelup.web;

import org.levelup.dao.UserDao;
import org.levelup.model.User;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.levelup.web.AppConstants.*;

@WebServlet(urlPatterns = LOGIN_SERVLET)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(USER_NAME_PARAMETER) != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String login = req.getParameter(USER_NAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        if (login == null || password == null) {
            resp.sendRedirect(LOGIN_PAGE + login);
            return;
        }
        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        UserDao dao = new UserDao(manager);
        User user = null;
        try {
            user = dao.findByLogin(login);
        } finally {
            manager.close();
        }

        if (user == null) {
            req.getSession().setAttribute("message", "user is null. login= " + login);
            resp.sendRedirect(LOGIN_PAGE + login);
            return;
        }

        if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
            req.getSession().setAttribute(VERIFIED_USER_NAME_ATTRIBUTE, login);
            resp.sendRedirect(req.getContextPath());
        } else {
            resp.sendRedirect(LOGIN_PAGE + login);
            req.getSession().setAttribute("message", "wrong login or password");
        }
    }
}
