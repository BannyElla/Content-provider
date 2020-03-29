package org.levelup.dao;

import javax.persistence.EntityManager;

public abstract class AbstractDao<T> {
    protected EntityManager manager;

    public AbstractDao(EntityManager manager) {
        this.manager = manager;
    }

    public T update(T obj) {
        verify(obj);
       return manager.merge(obj);
    }

    protected T persist(T obg) {
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

    protected abstract void verify(T obj);
}
