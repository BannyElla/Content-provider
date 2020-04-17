package org.levelup.repositories;

import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/* Этот репозиторий будет доступен по адресу /api/repository/user-api */
@Repository
@RepositoryRestResource(collectionResourceRel = "user-api",
        itemResourceRel = "user-api",
        path = "user-api") // Если spring не угадал название репозитория, можно указать своё название
public interface UsersRepository extends CrudRepository<User, Integer> {
    User findByLogin(@Param("login") String login);

    Page<User> findByRole_Name(@Param("role") UserRole roleName, Pageable page);

    /* POST-запрос по адресу /api/repository/user-api с json в теле создает новый объект */
    /* PUT-запрос по адресу /api/repository/user-api с json в теле обновляет существующий объект */
}
