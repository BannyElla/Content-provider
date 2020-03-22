package org.levelup.dao;

import com.sun.istack.Nullable;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.exception.GenericJDBCException;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDao extends AbstractDao implements Dao<Category> {

    public CategoryDao(EntityManager manager) {
        super(manager);
    }

    /**
     * By default the method sets VisibilityType.PRIVATE to visibility
     */
    @Override
    public Category create(Category newCategory) {
        verify(newCategory);
        return (Category) persist(newCategory);
    }

    @Override
    public Category update(Category category) throws Exception {
        verify(category);
        try {
            manager.getTransaction().begin();
            Category updatedCategory = manager.find(Category.class, category.getId());
            updatedCategory.setName(category.getName());
            updatedCategory.setVisibility(category.getVisibility());
            manager.getTransaction().commit();
            return updatedCategory;
        } catch (GenericJDBCException e) {
            throw new Exception("Name " + category.getName() + " can't be updated. " + e.getMessage());
        } catch (NonUniqueObjectException e) {
            throw new Exception("Name " + category.getName() + " has been already exist. " + e.getMessage());
        }
    }

    @Override
    public Long delete(Long id) throws Exception {
        try {
            manager.getTransaction().begin();
            Category category = manager.find(Category.class, id);
            manager.remove(category);
            manager.getTransaction().commit();
            return category.getId();
        } catch (Exception e) {
            throw new Exception("Category id = " + id + " can't be deleted. " + e.getMessage());
        }
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

    private void verify(Category category) {
        Objects.requireNonNull(category.getName(), "Category name must not be null");
        if (category.getVisibility() == null) {
            category.setVisibility(VisibilityType.PRIVATE);
        }
    }
}
