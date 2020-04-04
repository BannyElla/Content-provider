package org.levelup.web;

import org.levelup.dao.ArticleDao;
import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Article;
import org.levelup.model.Role;
import org.levelup.model.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.List;

import static org.levelup.web.AppConstants.*;

@WebListener
public class ApplicationListener implements ServletContextListener {
    EntityManagerFactory factory;
    EntityManager manager;
    ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        factory = Persistence.createEntityManagerFactory(DATA_BASE);
        context = servletContextEvent.getServletContext();
        context.setAttribute(FACTORY_ATTRIBUTE, factory);
        manager = factory.createEntityManager();
        checkAdmin();
        getArticles();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        EntityManagerFactory factory = (EntityManagerFactory) servletContextEvent
                .getServletContext()
                .getAttribute(FACTORY_ATTRIBUTE);
        if (factory != null) {
            factory.close();
        }
    }

    private void checkAdmin() {

        RoleDao roleDao = new RoleDao(manager);
        Role adminRole = roleDao.findByName(UserRole.ADMIN);
        if (adminRole == null) {
            adminRole = new Role(UserRole.ADMIN);
            roleDao.create(adminRole);
        }

        Role userRole = roleDao.findByName(UserRole.USER);
        if (userRole == null) {
            userRole = new Role(UserRole.USER);
            roleDao.create(userRole);
        }

        UserDao users = new UserDao(manager);
        if (users.findByRole(adminRole) == null) {
            users.create(ADMIN_LOGIN, ADMIN_PASSWORD, adminRole);
        }
    }

    private void getArticles() {
        ArticleDao dao = new ArticleDao(manager);
        List<Article> articles = dao.findAll();
        context.setAttribute(ALL_ARTICLES_ATTRIBUTE, articles);
    }
}
