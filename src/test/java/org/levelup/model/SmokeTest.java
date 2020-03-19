package org.levelup.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class SmokeTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    private static final String TEST_LOGIN = "Test user";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_IMAGE_PATH = "/test/path";
    private static final String TEST_CATEGORY_NAME = "Test category";
    private static final String TEST_ARTICLE_HEADER = "Test Header";
    private static final String TEST_ARTICLE_TEXT = "Test text text text text text text.";

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
    void createRole() {
        Role role = new Role();
        role.setName(RoleName.USER);

        persistObject(role);
        Assertions.assertNotEquals(0, role.getId(), "Role hasn't been created");
    }

    @Test
    void createUser() {
        Role role = new Role();
        role.setName(RoleName.USER);

        User user = new User();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setRole(role);

        persistObject(role);
        persistObject(user);
    }

    @Test
    void createCategory() {
        Category category = new Category();
        category.setName(TEST_CATEGORY_NAME);

        persistObject(category);
        Assertions.assertNotEquals(0, category.getId(), "Category hasn't been created");
    }

    @Test
    void createImage() {
        Image image = new Image();
        image.setPath(TEST_IMAGE_PATH);

        persistObject(image);
        Assertions.assertNotEquals(0, image.getId(), "Image hasn't been created");
    }

    @Test
    void createArticle() {
        Article article = new Article();
        article.setHeader(TEST_ARTICLE_HEADER);
        article.setText(TEST_ARTICLE_TEXT);

        persistObject(article);
        Assertions.assertNotEquals(0, article.getId(), "Article hasn't been created");
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }
}