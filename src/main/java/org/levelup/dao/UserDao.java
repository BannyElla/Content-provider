package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Role;
import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDao extends AbstractDao<User> {
    @Autowired
    public UserDao(EntityManager manager) {
        super(manager);
    }

    public User create(String login, String password, Role role) {
        User user = new User(login, password, role);
        verify(user);
        return persist(user);
    }

    @Nullable
    public User findByLogin(String login) {
        String sql = "select user from User user where user.login =: login";
        try {
            return manager.createQuery(sql, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findByRole(Role role) {
        String sql = "select user from User user where user.role =: role";
        return manager.createQuery(sql, User.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    protected void verify(User user) {
        Objects.requireNonNull(user.getLogin(), "Login must not be null");
        Objects.requireNonNull(user.getPassword(), "Password must not be null");
        if (user.getRole().getName() == null) {
            user.setRole(new Role(UserRole.USER));
        }
    }
}
