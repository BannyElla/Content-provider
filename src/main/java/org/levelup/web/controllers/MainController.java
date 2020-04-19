package org.levelup.web.controllers;

import org.levelup.model.Category;
import org.levelup.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.levelup.web.AppConstants.INDEX;

@Controller
public class MainController {
    @Autowired
    CategoryRepository categories;

    @GetMapping("/")
    public String mainPage(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("private_categories", categories.findPrivateCategories(getPage()).toList());
        model.addAttribute("public_categories", categories.findPublicCategories(getPage()).toList());
        return INDEX;
    }

    private PageRequest getPage() {
        return PageRequest.of(0, 10);
    }

    private List<Category> toList(Page<Category> categoryPage) {
        return categoryPage.get().collect(Collectors.toList());
    }
}
