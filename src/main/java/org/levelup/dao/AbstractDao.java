package org.levelup.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public abstract class AbstractDao<T> {
    protected final EntityManager manager;

    @Autowired
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
