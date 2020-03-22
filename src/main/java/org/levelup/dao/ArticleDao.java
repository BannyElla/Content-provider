package org.levelup.dao;

import com.sun.istack.Nullable;
import org.hibernate.exception.GenericJDBCException;
import org.levelup.model.Article;
import org.levelup.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleDao extends AbstractDao implements Dao<Article> {
    private Article article;

    public ArticleDao(EntityManager manager) {
        super(manager);
    }

    @Override
    public Article create(Article article) {
        verify(article);
        return (Article) persist(article);
    }

    @Override
    public Article update(Article article) throws Exception {
        verify(article);
        try {
            manager.getTransaction().begin();
            Article updatedArticle = manager.find(Article.class, article.getId());
            updatedArticle.setHeader(article.getHeader());
            updatedArticle.setText(article.getText());
            updatedArticle.setCategory(article.getCategory());
            updatedArticle.setImage(article.getImage());

            manager.getTransaction().commit();
            return updatedArticle;
        } catch (GenericJDBCException e) {
            throw new Exception("Article \"" + article.getHeader() + "\" can't be updated. " + e.getMessage());
        }
    }

    @Override
    public Long delete(Long id) throws Exception {
        try {
            manager.getTransaction().begin();
            Article article = manager.find(Article.class, id);
            manager.remove(article);
            manager.getTransaction().commit();
            return id;
        } catch (Exception e) {
            throw new Exception("Article id = " + id + " can't be deleted. " + e.getMessage());
        }
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
        try {
            return manager.createQuery(sql, Article.class)
                    .setParameter("category", category)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<Article> findAll() {
        String sql = "select article from Article article";
        try {
            return manager.createQuery(sql, Article.class)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    private void verify(Article article) {
        Objects.requireNonNull(article.getHeader(), "Header  must not be null");
        Objects.requireNonNull(article.getText(), "Text  must not be null");
        Objects.requireNonNull(article.getCategory(), "Category  must not be null");
    }
}
