package org.levelup.dao;

public interface Dao<T> {
   T create(T entity);

   T update(T entity) throws Exception;

   Long delete(Long id) throws Exception;

}
