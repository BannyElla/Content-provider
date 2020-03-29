package org.levelup.web;

import org.levelup.dao.UserDao;
import org.levelup.model.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.levelup.web.AppConstants.*;

//@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATA_BASE);
        servletContextEvent.getServletContext().setAttribute(FACTORY_ATTRIBUTE, factory);
        EntityManager manager = factory.createEntityManager();
        UserDao dao = new UserDao(manager);
        if (dao.findByRole(UserRole.ADMIN) == null) {
            dao.create(ADMIN_LOGIN, ADMIN_PASSWORD, UserRole.ADMIN);
        }
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
}
