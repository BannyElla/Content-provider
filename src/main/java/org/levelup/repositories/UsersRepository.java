package org.levelup.repositories;

import org.levelup.model.Role;
import org.levelup.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "user-api",
        itemResourceRel = "user-api",
        path = "user-api") // Если spring не угадал название репозитория, можно указать своё название
public interface UsersRepository extends CrudRepository<User, Integer> {
    User save(@Param("user") User user);
    User findByLogin(@Param("login") String login);
    List<User> findByRole(@Param("role")Role role);
}
