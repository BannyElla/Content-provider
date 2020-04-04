package org.levelup.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.model.Role;
import org.levelup.model.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class RoleDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private RoleDao dao;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestDb");
        manager = factory.createEntityManager();
        dao = new RoleDao(manager);
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
        Role role = new Role(UserRole.USER);
        Role actualRole = dao.create(role);

        creatingAssertions(actualRole);
        assertEquals(UserRole.USER, actualRole.getName());
    }

    @Test
    void findByName() {
        Role role = new Role(UserRole.ADMIN);
        persistObject(role);

        Role actualRole = dao.findByName(UserRole.ADMIN);
        creatingAssertions(actualRole);
        assertEquals(UserRole.ADMIN, actualRole.getName());
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    private void creatingAssertions(Role role) {
        assertNotNull(role, "Role hasn't been created");
        assertNotEquals(0, role.getId(), "Role hasn't been created");
    }
}