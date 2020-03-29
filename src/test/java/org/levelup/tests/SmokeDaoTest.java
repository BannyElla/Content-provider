package org.levelup.tests;

import org.junit.jupiter.api.Test;
import org.levelup.dao.CategoryDao;
import org.levelup.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
public class SmokeDaoTest {
    @Autowired
    private CategoryDao categoryDao;
    /*@Autowired
    private ArticleDao articleDao;*/

    @Test
    void smokeTest() {
        Category newCategory = new Category();
        newCategory.setName("category1");
        Category category = categoryDao.create(newCategory);
      /*  Article newArticle = new Article();
        newArticle.setHeader("header");
        newArticle.setText("text");
        newArticle.setCategory(category);
        Article article = articleDao.create(newArticle);*/
    }

}
