package org.levelup.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao<T> {
    @PersistenceContext
    protected EntityManager manager;

    @Transactional
    public long delete(long id, Class<T> type) throws Exception {
            T obj = manager.find(type, id);
            if (obj == null) {
                throw new Exception(type + " with id = " + id + " doesn't exist.");
            }
            manager.remove(obj);
            return id;
    }

    protected abstract void verify(T obj);
}
