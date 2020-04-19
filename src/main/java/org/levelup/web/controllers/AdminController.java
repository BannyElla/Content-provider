package org.levelup.web.controllers;

import org.levelup.dao.CategoryDao;
import org.levelup.model.Category;
import org.levelup.web.forms.CategoriesForm;
import org.levelup.web.forms.VisibilityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static org.levelup.web.AppConstants.*;

@Controller
public class AdminController {
    @Autowired
    private CategoryDao dao;
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

    @PostMapping(path = CREATE_CATEGORY_PAGE)
    public String create(ModelMap model, @ModelAttribute(CATEGORIES_FORM_ATTRIBUTE) CategoriesForm form, BindingResult validationResult) {
        model.addAttribute(VISIBILITY_ATTRIBUTE, createVisibilityList());
        Category newCategory = new Category();
        newCategory.setName(form.getName());
        newCategory.setVisibility(form.getVisible());
        try {
            dao.create(newCategory);
        }catch (Exception e) {
            validationResult.addError(
            new FieldError(CATEGORIES_FORM_ATTRIBUTE, "category name",
                    "Category with name " + form.getName()
                            + " is already registered."));
            return CREATE_CATEGORY;
        }

        return REDIRECT + CATEGORIES;
    }
}
