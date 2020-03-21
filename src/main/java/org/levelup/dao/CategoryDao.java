package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends Dao {

    public CategoryDao(EntityManager manager) {
        super(manager);
    }

    /**
     * By default the method sets VisibilityType.PRIVATE to visibility
     */
    public Category create(String name) {
        return create(name, VisibilityType.PRIVATE);
    }

    public Category create(String name, VisibilityType visible) {
        Category category = new Category();
        category.setName(name);
        category.setVisibility(visible);
        return (Category) persist(category);
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
        try {
            return manager.createQuery(sql, Category.class)
                    .setParameter("visible", visible)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<Category> findAll() {
        String sql = "select category from Category category";
        try {
            return manager.createQuery(sql, Category.class)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public boolean changeName(String newName) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }

    public boolean changeVisibility(VisibilityType newVisibility) {
        throw new UnsupportedOperationException("This method hasn't been implemented yet");
    }
}
