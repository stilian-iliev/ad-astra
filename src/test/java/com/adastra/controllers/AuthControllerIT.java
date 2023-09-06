package com.adastra.controllers;

import com.adastra.models.Publication;
import com.adastra.models.User;
import com.adastra.repositories.UserRepository;
import com.adastra.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    private UserRepository userRepository;

    private User testUser, testAdmin;

    @BeforeEach()
    void setUp() {
        testDataUtils.initRoles();
        testUser = testDataUtils.createTestUser("user", "password");
        testAdmin = testDataUtils.createTestAdmin("admin", "password");
    }

    @AfterEach
    void teardown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegistration() throws Exception {
        long initialUsers = userRepository.count();
        mockMvc.perform(post("/register")
                        .param("username", "username")
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Assertions.assertEquals(initialUsers + 1, userRepository.count());
    }

    @Test
    void testRegistrationWithBadCredentials() throws Exception {
        long initialUsers = userRepository.count();
        mockMvc.perform(post("/register")
                        .param("username", "username")
                        .param("password", "password")
                        .param("confirmPassword", "drowssap")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        mockMvc.perform(post("/register")
                        .param("username", testUser.getUsername())
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        mockMvc.perform(post("/register")
                        .param("username", "")
                        .param("password", "password")
                        .param("confirmPassword", "drowssap")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        mockMvc.perform(post("/register")
                        .param("username", "username")
                        .param("password", "")
                        .param("confirmPassword", "password")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        Assertions.assertEquals(initialUsers, userRepository.count());
    }

    @Test
    void loginPageShown() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLogin() throws Exception {
//        mockMvc.perform(post("/login")
//                        .param("username", testUser.getUsername())
//                        .param("password", testUser.getPassword())
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(forwardedUrl("/"));
    }

    @Test
    void testLoginWithBadCredentials() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "invalid")
                        .param("password", "invalid")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login-error"));
    }

}
