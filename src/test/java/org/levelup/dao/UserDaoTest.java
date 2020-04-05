package org.levelup.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.model.Role;
import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.levelup.tests.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = TestConfiguration.class)
@WebMvcTest
class UserDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    @Autowired
    UserDao userDao;

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
        persistObject(role);

        User actuallUser = userDao.create(login, password, role);
        assertNotNull(actuallUser);
        assertNotEquals(0, actuallUser.getId(), "User hasn't been created");
        assertEquals(login, actuallUser.getLogin());
        assertEquals(UserRole.USER, actuallUser.getRole().getName());
    }

    @Test
    void findByLogin() {
        String login = "test login 1";
        String password = "fjksdj8o43j";
        Role role = new Role(UserRole.USER);
        User user = new User(login, password, role);
        persistObject(role);
        persistObject(user);

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

        List<User> actualUsers = userDao.findByRole(role);
        assertNotNull(actualUsers);
        assertNotEquals(0, actualUsers.get(0).getId(), "User hasn't been created");
        assertEquals(login, actualUsers.get(0).getLogin());
        assertEquals(UserRole.USER, actualUsers.get(0).getRole().getName());
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

}