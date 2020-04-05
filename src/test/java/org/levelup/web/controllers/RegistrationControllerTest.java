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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {TestConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
class RegistrationControllerTest {
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
    void registrationFormView() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(REGISTRATION_PATH)).andExpect(status().isOk())
                .andExpect(view().name(REGISTRATION))
                .andReturn();
    }

    @Test
    void registrationProcess() throws Exception {
        roles.create(new Role(UserRole.USER));
        mockMvc.perform(
                MockMvcRequestBuilders.post(REGISTRATION_PATH)
                .param(USER_NAME_PARAMETER, "test5")
                .param(PASSWORD_PARAMETER, "1234"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + LOGIN_PAGE + "test5"))
                .andReturn();
    }

}