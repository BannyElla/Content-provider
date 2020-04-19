package org.levelup.web.controllers;

import org.levelup.model.Article;
import org.levelup.model.Category;
import org.levelup.repositories.ArticleRepository;
import org.levelup.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.levelup.web.AppConstants.ALL_ARTICLES;
import static org.levelup.web.AppConstants.ALL_ARTICLES_PAGE;

@Controller
public class ArticleController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(ALL_ARTICLES_PAGE)
    public String allArticles(Model model) {
        Map<Category, List<Article>> catalog = new HashMap<>();
        for (Category category : categoryRepository.findAll()) {
            catalog.put(category, null);
        }
        catalog.replaceAll((c, v) -> articleRepository.findByCategory(c));
        model.addAttribute("catalog", catalog);
        return ALL_ARTICLES;
    }

    @GetMapping("/article/{category-id}/{id}")
    public String getArticle(@PathVariable("category-id") Long categoryId, @PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).get();
        model.addAttribute("article", article);
        return "article";
    }
}
