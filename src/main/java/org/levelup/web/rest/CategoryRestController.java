package org.levelup.web.rest;

import org.levelup.dao.CategoryDao;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;
import org.levelup.repositories.CategoryRepository;
import org.levelup.web.rest.response.CategoryStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryRestController {
    @Autowired
    CategoryRepository repository;
    @Autowired
    CategoryDao dao;

    @GetMapping("/api/categories")
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        repository.findAll().forEach(categories::add);
        return categories;
    }

    @GetMapping("/api/public-categories")
    public List<Category> getPublicCategories() {
        return repository.findPublicCategories();
    }

    @GetMapping("/api/create-category")
    public Category create(@RequestParam String name, @RequestParam(required = false) String visibility) {
        VisibilityType visible = StringUtils.isEmpty(visibility) ?
                VisibilityType.PUBLIC :
                VisibilityType.valueOf(visibility.toUpperCase());
        Category newCategory = new Category(name, visible);
        return repository.save(newCategory);
    }

    @GetMapping("/api/delete-category-by_id")
    public String delete(@RequestParam Long id) {
        try {
            dao.delete(id);
            return "Category with id=" + id + " was deleted successfully";
        } catch (Exception e) {
            return "Some error happened during deleting of the category with id=" + id;
        }
    }

    @GetMapping("/api/category-statistic")
    public CategoryStatistic getStatistic() {
        CategoryStatistic statistic = new CategoryStatistic();
        statistic.setCountAllCategories(repository.count());
        statistic.setCountPrivateCategories(repository.countPrivateCategories());
        statistic.setCountPublicCategories(repository.countPublicCategories());
        statistic.setCountArticlesInAllCategories(repository.countArticlesInAllCategories());
        statistic.setCountArticlesInPrivateCategories(repository.countArticlesInPrivateCategories());
        statistic.setCountArticlesInPublicCategories(repository.countArticlesInPublicCategories());
        return statistic;
    }
}
