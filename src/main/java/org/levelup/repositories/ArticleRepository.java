package org.levelup.repositories;

import org.levelup.model.Article;
import org.levelup.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    List<Article> findByCategory(Category category);
}
