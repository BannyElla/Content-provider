package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Role;
import org.levelup.model.RoleName;
import org.levelup.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private EntityManager manager;

    public UserDao(EntityManager manager) {
        this.manager = manager;
    }

    public User create(String login, String password, Role role) {
        User user = new User(login, password, role);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        manager.getTransaction().commit();
        return user;
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

    public List<User> findByRole(RoleName role) {
        String sql = "select user from User user where user.role =: roleName";
        try {
            return manager.createQuery(sql, User.class)
                    .setParameter("roleName", role)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

}
