package org.levelup.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private CategoryDao dao;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestDb");
        manager = factory.createEntityManager();
        dao = new CategoryDao(manager);
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
    void createPrivateCategory() {
        String name = "category";
        Category newCategory = new Category();
        newCategory.setName(name);
        Category actualCategory = dao.create(newCategory);

        creatingAssertions(actualCategory);
        assertEquals(name, actualCategory.getName());
        assertEquals(VisibilityType.PRIVATE, actualCategory.getVisibility());
    }

    @Test
    void createPublicCategory() {
        String name = "category 2";
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setVisibility(VisibilityType.PUBLIC);
        Category actualCategory = dao.create(newCategory);

        creatingAssertions(actualCategory);
        assertEquals(name, actualCategory.getName());
        assertEquals(VisibilityType.PUBLIC, actualCategory.getVisibility());
    }

    @Test
    void update() throws Exception {
        String name = "category 12";
        String newName = "category 13";
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setVisibility(VisibilityType.PUBLIC);
        persistObject(newCategory);

        Category updatedCategory = new Category();
        updatedCategory.setId(newCategory.getId());
        updatedCategory.setName(newName);
        updatedCategory.setVisibility(VisibilityType.PRIVATE);

        Category actualCategory = dao.update(updatedCategory);

        assertNotNull(actualCategory);
        assertEquals(newName, actualCategory.getName());
        assertEquals(VisibilityType.PRIVATE, actualCategory.getVisibility());
    }

    @Test
    void delete() throws Exception {
        String name = "category 14";
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setVisibility(VisibilityType.PUBLIC);
        persistObject(newCategory);
        Long expectedId = newCategory.getId();

        Long actualId = dao.delete(expectedId);
        assertNotNull(actualId);
        assertEquals(actualId, expectedId);
    }

    @Test
    void findByName() {
        String name = "category 3";
        Category category = new Category();
        category.setName(name);
        persistObject(category);

        Category actualCategory = dao.findByName(name);
        creatingAssertions(actualCategory);
        assertEquals(name, actualCategory.getName());
    }

    @Test
    void findPublicCategories() {
        String name = "category 4";
        Category category = new Category();
        category.setName(name);
        category.setVisibility(VisibilityType.PUBLIC);
        String name2 = "category 5";
        Category category2 = new Category();
        category2.setName(name2);
        category2.setVisibility(VisibilityType.PUBLIC);
        String name3 = "category 6";
        Category category3 = new Category();
        category3.setName(name3);
        category3.setVisibility(VisibilityType.PRIVATE);
        persistObject(category);
        persistObject(category2);
        persistObject(category3);

        List<Category> actualCategories = dao.findPublicCategories();
        assertSeveralCategory(2, actualCategories);
    }

    @Test
    void findPrivateCategories() {
        String name = "category 7";
        Category category = new Category();
        category.setName(name);
        category.setVisibility(VisibilityType.PUBLIC);
        String name2 = "category 8";
        Category category2 = new Category();
        category2.setName(name2);
        category2.setVisibility(VisibilityType.PUBLIC);
        String name3 = "category 9";
        Category category3 = new Category();
        category3.setName(name3);
        category3.setVisibility(VisibilityType.PRIVATE);
        persistObject(category);
        persistObject(category2);
        persistObject(category3);

        List<Category> actualCategories = dao.findPrivateCategories();
        assertSeveralCategory(1, actualCategories);
    }

    @Test
    void findPrivateCategoriesWhenTheyNotExist() {
        String name = "category 10";
        Category category = new Category();
        category.setName(name);
        category.setVisibility(VisibilityType.PUBLIC);
        persistObject(category);

        List<Category> actualCategories = dao.findPrivateCategories();
        assertSeveralCategory(0, actualCategories);
    }

    @Test
    void findAll() {
        String name = "category 11";
        Category category = new Category();
        category.setName(name);
        category.setVisibility(VisibilityType.PUBLIC);
        String name2 = "category 12";
        Category category2 = new Category();
        category2.setName(name2);
        category2.setVisibility(VisibilityType.PUBLIC);
        String name3 = "category 13";
        Category category3 = new Category();
        category3.setName(name3);
        category3.setVisibility(VisibilityType.PRIVATE);
        persistObject(category);
        persistObject(category2);
        persistObject(category3);

        List<Category> actualCategories = dao.findAll();
        assertSeveralCategory(3, actualCategories);
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    private void creatingAssertions(Category category) {
        assertNotNull(category, "Category hasn't been created");
        assertNotEquals(0, category.getId(), "Category hasn't been created");
    }

    private void assertSeveralCategory(int expectedCount, List<Category> actualCategory) {
        assertNotNull(actualCategory);
        assertEquals(expectedCount, actualCategory.size());
    }
}