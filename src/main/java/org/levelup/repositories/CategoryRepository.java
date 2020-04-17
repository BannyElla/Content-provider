package org.levelup.repositories;

import org.levelup.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Category save(@Param("category") Category category);

    @Query("select c from Category c where c.visible ='PUBLIC'")
    List<Category> findPublicCategories();

    @Query("select c from Category c where c.visible ='PRIVATE'")
    List<Category> findPrivateCategories();

    @Query("select count(a.id) from Category c, Article a")
    int countArticlesInAllCategories();

    @Query("select count(c.id) from Category c where c.visible ='PRIVATE'")
    int countPrivateCategories();

    @Query("select count(c.id) from Category c where c.visible ='PUBLIC'")
    int countPublicCategories();

    @Query("select count(a.id) from Category c, Article a where c.visible ='PRIVATE'")
    int countArticlesInPrivateCategories();

    @Query("select count(a.id) from Category c, Article a where c.visible ='PUBLIC'")
    int countArticlesInPublicCategories();
}
