package org.levelup.web.controllers;

import org.levelup.dao.RoleDao;
import org.levelup.model.Role;
import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.levelup.repositories.UsersRepository;
import org.levelup.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

import static org.levelup.web.AppConstants.*;

@Controller
public class RegistrationController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleDao roles;

    @Autowired
    private PasswordEncoder encoder;

    @ModelAttribute(FORM_ATTRIBUTE)
    public RegistrationForm createForm() {
        return new RegistrationForm("", "");
    }

    @GetMapping(path = REGISTRATION_PAGE)
    public String registrationPage(ModelMap model, @ModelAttribute(FORM_ATTRIBUTE) RegistrationForm form, Principal principal) {
        if (principal != null) {
            return REDIRECT;
        }
        model.addAttribute(FORM_ATTRIBUTE, createForm());
        return REGISTRATION;
    }

    @PostMapping(path = REGISTRATION_PAGE)
    public String processRegistration(ModelMap model,
                                      @Validated
                                      @ModelAttribute(FORM_ATTRIBUTE) RegistrationForm form,
                                      BindingResult validationResult) {
        Role userRole = roles.findByName(UserRole.USER);
        try {
            String encodedPassword = encoder.encode(form.getPassword());
            usersRepository.save(new User(form.getLogin(), encodedPassword, userRole));
        } catch (Throwable e) {
            validationResult.addError(
                    new FieldError(FORM_ATTRIBUTE, "login",
                            "User with login " + form.getLogin()
                                    + " is already registered."));
            return REGISTRATION;
        }
        return REDIRECT + LOGIN_PAGE_WITH_PARAMETER + form.getLogin();
    }
}
