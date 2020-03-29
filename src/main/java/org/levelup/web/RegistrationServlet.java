package org.levelup.web;

import org.levelup.dao.UserDao;
import org.levelup.model.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATA_BASE);
        EntityManager manager = factory.createEntityManager();
        // EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
        UserDao user = new UserDao(manager);
        try {
            user.create(login, password, UserRole.USER);
        } finally {
            manager.close();
        }
        resp.sendRedirect(req.getContextPath() + LOGIN_PAGE + login);
    }

}
