package org.levelup.repositories;

import org.levelup.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/* Этот репозиторий будет доступен по адресу /api/repository/categories */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("select c from Category c where c.visible ='PUBLIC'")
    Page<Category> findPublicCategories(Pageable page);

    @Query("select c from Category c where c.visible ='PRIVATE'")
    Page<Category> findPrivateCategories(Pageable page);

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
