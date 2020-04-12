package org.levelup.repositories;

import org.levelup.model.Role;
import org.levelup.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {
    User save(User user);
    User findByLogin(String login);
    List<User> findByRole(Role role);
}
