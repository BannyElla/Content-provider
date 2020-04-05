package org.levelup.web;

import org.levelup.dao.ArticleDao;
import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Article;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.levelup.web.AppConstants.*;

@Component
public class StartupListener {
    @Autowired
    RoleDao roles;
    @Autowired
    UserDao users;
    @Autowired
    ArticleDao articles;

    @EventListener
    @Transactional
    public void applicationStarted(ContextRefreshedEvent event) {
        checkAdmin();
        //getArticles(model);
    }

    private void checkAdmin() {
        Role adminRole = roles.findByName(UserRole.ADMIN);
        if (adminRole == null) {
            adminRole = new Role(UserRole.ADMIN);
            roles.create(adminRole);
        }

        Role userRole = roles.findByName(UserRole.USER);
        if (userRole == null) {
            userRole = new Role(UserRole.USER);
            roles.create(userRole);
        }

        if (users.findByRole(adminRole) == null) {
            users.create(ADMIN_LOGIN, ADMIN_PASSWORD, adminRole);
        }
    }

    private void getArticles(ModelMap model) {
        List<Article> articleList = articles.findAll();
        model.addAttribute(ALL_ARTICLES_ATTRIBUTE, articleList);
    }
}
