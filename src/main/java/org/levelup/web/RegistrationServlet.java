package org.levelup.web;

import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Role;
import org.levelup.model.User;
import org.levelup.model.UserRole;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.levelup.web.AppConstants.*;

@WebServlet(urlPatterns = REGISTRATION_SERVLET)
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationForm form = new RegistrationForm();
        form.setLogin("");
        form.setPassword("");

        req.setAttribute(FORM_ATTRIBUTE, form);
        req.getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(USER_NAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        UserDao users = new UserDao(manager);
        RoleDao roles = new RoleDao(manager);
        Role userRole = roles.findByName(UserRole.USER);
        User user = null;
        try {
            user = users.create(login, password, userRole);
        } finally {
            manager.close();
        }
        req.getSession().setAttribute("message", "registration id = " + user.getId());
        resp.sendRedirect(req.getContextPath());
    }

}
