package org.levelup.web;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

import static org.levelup.web.AppConstants.FACTORY_ATTRIBUTE;

public class PersistenceUtils {
    public static EntityManager createManager(ServletContext context) {
        EntityManagerFactory factory = (EntityManagerFactory) context.getAttribute(FACTORY_ATTRIBUTE);
        if (factory == null) {
            throw new IllegalStateException("No EntityManagerFactory found in the context");
        }
        return factory.createEntityManager();
    }
}
