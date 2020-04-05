package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Article;
import org.levelup.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class ArticleDao extends AbstractDao<Article> implements Dao<Article> {

    @Override
    @Transactional
    public Article create(Article article) {
        verify(article);
        manager.persist(article);
        return article;
    }

    @Transactional
    public Article update(Article article) {
        verify(article);
        Article oldArticle = manager.find(Article.class, article.getId());
        oldArticle.setHeader(article.getHeader());
        oldArticle.setText(article.getText());
        oldArticle.setImage(article.getImage());
        oldArticle.setCategory(article.getCategory());
        return manager.merge(oldArticle);
    }

    @Transactional
    public long delete(long id) throws Exception {
        return delete(id, Article.class);
    }

    @Nullable
    public Article findByHeader(String header) {
        String sql = "select article from Article article where article.header =: header";
        try {
            return manager.createQuery(sql, Article.class)
                    .setParameter("header", header)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Article> findByCategory(Category category) {
        String sql = "select article from Article article where article.category =: category";
        return manager.createQuery(sql, Article.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Article> findAll() {
        String sql = "select article from Article article";
        return manager.createQuery(sql, Article.class)
                .getResultList();
    }

    @Override
    protected void verify(Article article) {
        Objects.requireNonNull(article, "Article must not be null");
        Objects.requireNonNull(article.getHeader(), "Header  must not be null");
        Objects.requireNonNull(article.getText(), "Text  must not be null");
        Objects.requireNonNull(article.getCategory(), "Category  must not be null");
    }
}
