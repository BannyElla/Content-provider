package org.levelup.web;

import org.levelup.dao.ArticleDao;
import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static org.levelup.web.AppConstants.ADMIN;
import static org.levelup.web.AppConstants.ADMIN_PASSWORD;

@Component
public class StartupListener {
    @Autowired
    RoleDao roles;
    @Autowired
    UserDao users;
    @Autowired
    ArticleDao articles;
    @Autowired
    PasswordEncoder encoder;


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

        if (CollectionUtils.isEmpty(users.findByRole(adminRole))) {
            users.create(ADMIN, encoder.encode(ADMIN_PASSWORD), adminRole);
        }
    }
}
