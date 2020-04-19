package org.levelup.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup.dao.RoleDao;
import org.levelup.dao.UserDao;
import org.levelup.tests.TestConfiguration;
import org.levelup.web.configuration.WebConfiguration;
import org.levelup.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

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
    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter securityFilter;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(securityFilter)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void registrationFormView() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(REGISTRATION_PAGE)).andExpect(status().isOk())
                .andExpect(view().name(REGISTRATION))
                .andReturn();
    }

    @Test
    void registrationProcess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(REGISTRATION_PAGE)
                        .flashAttr(FORM_ATTRIBUTE, new RegistrationForm("test8","12356")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + LOGIN_PAGE_WITH_PARAMETER + "test8"))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testRegistrationFormAlreadyLoggedIn() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(REGISTRATION_PAGE)
        ).andExpect(status().is3xxRedirection())
                .andReturn();
    }
}