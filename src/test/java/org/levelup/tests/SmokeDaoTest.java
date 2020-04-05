package org.levelup.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.dao.ArticleDao;
import org.levelup.dao.CategoryDao;
import org.levelup.model.Article;
import org.levelup.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = TestConfiguration.class)
@WebMvcTest
public class SmokeDaoTest {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleDao articleDao;

    @Test
    void smokeTest() {
        Category newCategory = new Category();
        newCategory.setName("category1");
        Category category = categoryDao.create(newCategory);
        Article newArticle = new Article();
        newArticle.setHeader("header");
        newArticle.setText("text");
        newArticle.setCategory(category);
        articleDao.create(newArticle);
    }

}
