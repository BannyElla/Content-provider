package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Article;
import org.levelup.model.Category;
import org.levelup.model.Image;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao  extends Dao {
    private Article article;

    public ArticleDao(EntityManager manager) {
        super(manager);
    }

    public Article create(String header, String text, Category category) {
        article = new Article();
        init(header, text, category);
        return (Article) persist(article);
    }

    public Article create(String header, String text, Category category, Image image) {
        article = new Article();
        init(header, text, category);
        article.setImage(image);
        return (Article) persist(article);
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

    public boolean delete(Long id) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }

    public boolean updateHeader(Long id, String newHeader) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }

    public boolean updateText(Long id, String newText) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }

    public boolean updateImage(Long articleId, Long imageId, Image newImage) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }

    private void init(String header, String text, Category category) {
        article.setHeader(header);
        article.setText(text);
        article.setCategory(category);
    }
}
