package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoryDao extends AbstractDao<Category> implements Dao<Category> {
    @Autowired
    public CategoryDao(EntityManager manager) {
        super(manager);
    }

    /**
     * By default the method sets VisibilityType.PRIVATE to visibility
     */
    @Override
    public Category create(Category newCategory) {
        verify(newCategory);
        return persist(newCategory);
    }

    public long delete(long id) throws Exception {
        return delete(id, Category.class);
    }

    @Nullable
    public Category findByName(String name) {
        String sql = "select category from Category category where category.name =: name";
        try {
            return manager.createQuery(sql, Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Category> findPublicCategories() {
        return findByVisibility(VisibilityType.PUBLIC);
    }

    public List<Category> findPrivateCategories() {
        return findByVisibility(VisibilityType.PRIVATE);
    }

    private List<Category> findByVisibility(VisibilityType visible) {
        String sql = "select category from Category category where category.visible =: visible";
        return manager.createQuery(sql, Category.class)
                .setParameter("visible", visible)
                .getResultList();
    }

    public List<Category> findAll() {
        String sql = "select category from Category category";
        return manager.createQuery(sql, Category.class)
                .getResultList();
    }

    protected void verify(Category category) {
        Objects.requireNonNull(category, "Category must not be null");
        Objects.requireNonNull(category.getName(), "Category name must not be null");
        if (category.getVisibility() == null) {
            category.setVisibility(VisibilityType.PRIVATE);
        }
    }
}
