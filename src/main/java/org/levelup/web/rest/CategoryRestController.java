package org.levelup.web.rest;

import org.levelup.dao.CategoryDao;
import org.levelup.model.Category;
import org.levelup.model.VisibilityType;
import org.levelup.repositories.CategoryRepository;
import org.levelup.web.rest.response.CategoryStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryRestController {
    @Autowired
    CategoryRepository repository;
    @Autowired
    CategoryDao dao;

    @GetMapping("/api/categories") // путь от корня, по которому будет доступен этот get-запрос
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        repository.findAll().forEach(categories::add);
        return categories;
    }

    @GetMapping("/api/public-categories")
    public List<Category> getPublicCategories(@RequestParam int pageNumber) {
        Page<Category> categoryPage = repository.findPublicCategories(PageRequest.of(pageNumber - 1, 3));
        return categoryPage.get().collect(Collectors.toList());
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
