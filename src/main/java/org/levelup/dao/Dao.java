package org.levelup.dao;

public interface Dao<T> {
   T create(T entity);

   T update(T entity) throws Exception;

   long delete(long id) throws Exception;

}
