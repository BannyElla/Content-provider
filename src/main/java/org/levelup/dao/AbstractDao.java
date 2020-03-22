package org.levelup.dao;

import javax.persistence.EntityManager;

public abstract class AbstractDao {
    protected EntityManager manager;

    public AbstractDao(EntityManager manager) {
        this.manager = manager;
    }

    protected Object persist(Object obg) {
        manager.getTransaction().begin();
        try {
            manager.persist(obg);
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        manager.getTransaction().commit();
        return obg;
    }
}
