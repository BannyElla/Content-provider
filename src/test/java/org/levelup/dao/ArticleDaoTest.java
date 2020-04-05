package org.levelup.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.model.Article;
import org.levelup.model.Category;
import org.levelup.model.Image;
import org.levelup.tests.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
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
class ArticleDaoTest {
    private static final String TEST_IMAGE_PATH = "/test/image/path";
    private EntityManagerFactory factory;
    private EntityManager manager;
    @Autowired
    private ArticleDao dao;
    private Image image;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestDb");
        manager = factory.createEntityManager();
        image = new Image(TEST_IMAGE_PATH);
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
        String header = "header1";
        String text = "text text 1";
        String categoryName = "category 1";
        Category category = new Category();
        category.setName(categoryName);
        persistObject(image);
        persistObject(category);

        Article newArticle = new Article();
        newArticle.setHeader(header);
        newArticle.setText(text);
        newArticle.setCategory(category);
        newArticle.setImage(image);
        Article article = dao.create(newArticle);
        assertOneArtile(header, text, categoryName, article, TEST_IMAGE_PATH);
    }

    @Test
    void update() {
        String header = "header11";
        String text = "text text 11";
        String categoryName = "category 11";
        Category category = new Category();
        category.setName(categoryName);
        persistObject(image);
        persistObject(category);

        Article article = new Article();
        article.setHeader(header);
        article.setText(text);
        article.setCategory(category);
        article.setImage(image);
        persistObject(article);

        String newHeader = "header12";
        String newText = "text text 12";
        String newCategoryName = "category 12";
        Category newCategory = new Category();
        newCategory.setName(newCategoryName);
        persistObject(newCategory);
        Article updatedArticle = new Article();
        updatedArticle.setId(article.getId());
        updatedArticle.setHeader(newHeader);
        updatedArticle.setText(newText);
        updatedArticle.setCategory(newCategory);

        Article actualArticle = dao.update(updatedArticle);
        assertOneArtile(newHeader, newText, newCategoryName, actualArticle, null);
    }

    @Test
    void delete() throws Exception {
        String header = "header13";
        String text = "text text 13";
        String categoryName = "category 13";
        Category category = new Category();
        category.setName(categoryName);
        persistObject(image);
        persistObject(category);

        Article article = new Article();
        article.setHeader(header);
        article.setText(text);
        article.setCategory(category);
        article.setImage(image);
        persistObject(article);

        Long expectedId = article.getId();

        Long actualId = dao.delete(expectedId);
        assertNotNull(actualId);
        assertEquals(actualId, expectedId);
    }

    @Test
    void findByHeader() {
        String categoryName = "category 2";
        String header = "header2";
        String text = "text text 2";
        String header2 = "header3";
        String text2 = "text text 3";
        Category category = new Category();
        category.setName(categoryName);
        persistObject(category);
        createArticle(header, text, category);
        createArticle(header2, text2, category);

        Article actualArticle = dao.findByHeader(header);
        assertOneArtile(header, text, categoryName, actualArticle, TEST_IMAGE_PATH);
    }

    @Test
    void findByCategory() {
        String categoryName = "category 2";
        String categoryName2 = "category 3";
        String header = "header2";
        String text = "text text 2";
        String header2 = "header3";
        String text2 = "text text 3";
        Category category = new Category();
        category.setName(categoryName);
        Category category2 = new Category();
        category2.setName(categoryName2);
        persistObject(category);
        persistObject(category2);
        createArticle(header, text, category);
        createArticle(header2, text2, category);

        List<Article> actualArticles = dao.findByCategory(category);
        assertSeveralArticles(2, actualArticles);
    }

    @Test
    void findAll() {
        String categoryName = "category 4";
        String categoryName2 = "category 5";
        String header = "header4";
        String text = "text text 4";
        String header2 = "header5";
        String text2 = "text text 5";
        Category category = new Category();
        category.setName(categoryName);
        Category category2 = new Category();
        category2.setName(categoryName2);
        persistObject(category);
        persistObject(category2);
        createArticle(header, text, category);
        createArticle(header2, text2, category2);

        List<Article> actualArticles = dao.findAll();
        assertSeveralArticles(2, actualArticles);
    }

    private void createArticle(String header, String text, Category category) {
        Article article = new Article();
        article.setHeader(header);
        article.setText(text);
        article.setCategory(category);
        article.setImage(image);
        persistObject(image);
        persistObject(article);
    }

    private void persistObject(Object obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    private void assertOneArtile(String header, String text, String categoryName, Article article, String imagePath) {
        assertNotNull(article, "Article hasn't been created");
        assertNotEquals(0, article.getId(), "Article hasn't been created");
        assertEquals(header, article.getHeader());
        assertEquals(text, article.getText());
        assertEquals(categoryName, article.getCategory().getName());
        if (imagePath == null) {
            assertNull(article.getImage());
        } else {
            assertEquals(imagePath, article.getImage().getPath());
        }
    }

    private void assertSeveralArticles(int expectedCount, List<Article> actualArticles) {
        assertNotNull(actualArticles);
        assertEquals(expectedCount, actualArticles.size());
    }
}