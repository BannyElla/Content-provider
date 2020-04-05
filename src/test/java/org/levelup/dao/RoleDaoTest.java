package org.levelup.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.levelup.tests.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = TestConfiguration.class)
@WebMvcTest
class RoleDaoTest {

    @Autowired
    private RoleDao dao;

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
        dao.create(role);

        Role actualRole = dao.findByName(UserRole.ADMIN);
        creatingAssertions(actualRole);
        assertEquals(UserRole.ADMIN, actualRole.getName());
    }

    private void creatingAssertions(Role role) {
        assertNotNull(role, "Role hasn't been created");
        assertNotEquals(0, role.getId(), "Role hasn't been created");
    }
}