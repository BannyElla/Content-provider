package org.levelup.web.controllers;

import org.levelup.dao.CategoryDao;
import org.levelup.model.Article;
import org.levelup.model.Category;
import org.levelup.repositories.ArticleRepository;
import org.levelup.repositories.CategoryRepository;
import org.levelup.web.forms.CategoriesForm;
import org.levelup.web.forms.VisibilityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.levelup.web.AppConstants.*;

@Controller
public class AdminController {
    @Autowired
    private CategoryDao dao;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ArticleRepository articleRepository;
/*    @ModelAttribute(CATEGORIES_FORM_ATTRIBUTE)
    public CategoriesForm createCategoriesForm() {
        return new CategoriesForm("", VisibilityType.PUBLIC);
    }*/

    public VisibilityList createVisibilityList() {
        return new VisibilityList();
    }

    @GetMapping(path = CRUD_CATEGORIES_PAGE)
    public String categories(ModelMap model) {
        List<Category> categories = dao.findAll();
        model.addAttribute(CATEGORIES, categories);
        return CATEGORIES;
    }

    @GetMapping(path = CREATE_CATEGORY_PAGE)
    public String createCategoryPage(ModelMap model, @ModelAttribute(CATEGORIES_FORM_ATTRIBUTE) CategoriesForm form) {
        model.addAttribute(VISIBILITY_ATTRIBUTE, createVisibilityList());
        return CREATE_CATEGORY;
    }

    @GetMapping("/admin/create-random-articles")
    public String createArticles(Model model) {
        Map<Category, List<Article>> catalog = new HashMap<>();
        String text = "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.";
        for (Category category : repository.findAll()) {
            for (int i = 1; i <= 3; i++) {
                articleRepository.save(new Article("Article-" + i, text, category));
                catalog.put(category, null);
            }
        }
        catalog.replaceAll((c, v) -> articleRepository.findByCategory(c));
        model.addAttribute("catalog", catalog);
        return ALL_ARTICLES;
    }

    @PostMapping(path = CREATE_CATEGORY_PAGE)
    public String create(ModelMap model, @ModelAttribute(CATEGORIES_FORM_ATTRIBUTE) CategoriesForm form, BindingResult validationResult) {
        model.addAttribute(VISIBILITY_ATTRIBUTE, createVisibilityList());
        Category newCategory = new Category();
        newCategory.setName(form.getName());
        newCategory.setVisibility(form.getVisible());
        try {
            dao.create(newCategory);
        } catch (Exception e) {
            validationResult.addError(
                    new FieldError(CATEGORIES_FORM_ATTRIBUTE, "category name",
                            "Category with name " + form.getName()
                                    + " is already registered."));
            return CREATE_CATEGORY;
        }

        return REDIRECT + CATEGORIES;
    }
}
