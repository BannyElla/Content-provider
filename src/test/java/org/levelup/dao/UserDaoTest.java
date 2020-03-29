package org.levelup.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.levelup.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestDb");
        manager = factory.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    void create() {
        String login = "test login";
        String password = "12345TR!";
        Role role = new Role(UserRole.USER);
        User user = new User(login, password, role);
        persistObject(role);
        persistObject(user);
        assertNotNull(user);
        assertNotEquals(0, user.getId(), "User hasn't been created");
        assertEquals(login, user.getLogin());
        assertEquals(UserRole.USER, user.getRole().getName());
    }

    @Test
    void findByLogin() {
        String login = "test login 1";
        String password = "fjksdj8o43j";
        Role role = new Role(UserRole.USER);
        User user = new User(login, password, role);
        persistObject(role);
        persistObject(user);

        UserDao userDao = new UserDao(manager);
        User actualUser = userDao.findByLogin(login);
        assertNotNull(actualUser);
        assertNotEquals(0, actualUser.getId(), "User hasn't been created");
        assertEquals(login, actualUser.getLogin());
        assertEquals(UserRole.USER, actualUser.getRole().getName());
    }

    @Test
    void findByRole() {
        String login = "test login 1";
        String password = "fjksdj8o43j";
        Role role = new Role(UserRole.USER);
        User user = new User(login, password, role);
        persistObject(role);
        persistObject(user);

        UserDao userDao = new UserDao(manager);
        User actualUser = userDao.findByLogin(login);
        assertNotNull(actualUser);
        assertNotEquals(0, actualUser.getId(), "User hasn't been created");
        assertEquals(login, actualUser.getLogin());
        assertEquals(UserRole.USER, actualUser.getRole().getName());
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

}