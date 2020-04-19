package org.levelup.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.levelup.tests.TestConfiguration;
import org.levelup.web.configuration.WebConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.levelup.web.AppConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {TestConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
class LoginControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserDao users;
    @Autowired
    private RoleDao roles;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void loginFormView() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(LOGIN_PAGE)).andExpect(status().isOk())
                .andExpect(view().name(LOGIN))
                .andReturn();
    }

    @Test
    void sessionAttr() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(LOGIN_PAGE).sessionAttr(VERIFIED_USER_NAME_ATTRIBUTE, "test1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void validLogin() throws Exception {
        Role role = roles.create(new Role(UserRole.USER));
        users.create("test", "123", role);

        mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_PAGE)
                        .param(USER_NAME_PARAMETER, "test")
                        .param(PASSWORD_PARAMETER, "123"))
                .andExpect(request().sessionAttribute(VERIFIED_USER_NAME_ATTRIBUTE, "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT))
                .andReturn();
    }

    @Test
    void wrongLogin() throws Exception {
        Role role = roles.create(new Role(UserRole.USER));
        users.create("test1", "123", role);

        mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_PAGE)
                        .param(USER_NAME_PARAMETER, "wrong-test")
                        .param(PASSWORD_PARAMETER, "123"))
                .andExpect(request().sessionAttribute("message", "wrong login or password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + LOGIN_PAGE_WITH_PARAMETER))
                .andReturn();
    }

    @Test
    void wrongPassword() throws Exception {
        Role role = roles.create(new Role(UserRole.USER));
        users.create("test2", "321", role);

        mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_PAGE)
                        .param(USER_NAME_PARAMETER, "test2")
                        .param(PASSWORD_PARAMETER, "123"))
                .andExpect(request().sessionAttribute("message", "wrong login or password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + LOGIN_PAGE_WITH_PARAMETER))
                .andReturn();
    }

    @Test
    void userHasAlreadyLogged() throws Exception {
        Role role = roles.create(new Role(UserRole.USER));
        users.create("test3", "333", role);
        mockMvc.perform(
                MockMvcRequestBuilders.post(LOGIN_PAGE)
                        .param(USER_NAME_PARAMETER, "test3")
                        .param(PASSWORD_PARAMETER, "333")
                .sessionAttr(VERIFIED_USER_NAME_ATTRIBUTE, "test3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT))
                .andReturn();
    }
}