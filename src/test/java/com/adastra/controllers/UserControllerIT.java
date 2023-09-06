package com.adastra.controllers;
import com.adastra.models.Publication;
import com.adastra.models.User;
import com.adastra.repositories.UserRepository;
import com.adastra.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User testUser, testAdmin;

    private Publication testUserPublication, testAdminPublication;

    @BeforeEach()
    void setUp() {
        testDataUtils.initRoles();
        testUser = testDataUtils.createTestUser("user", "password");
        testAdmin = testDataUtils.createTestAdmin("admin", "password");

        testUserPublication = testDataUtils.createTestPublication("userPublication", "userPublicationImage", testUser);
        testAdminPublication = testDataUtils.createTestPublication("adminPublication", "adminPublicationImage", testUser);
    }

    @AfterEach
    void teardown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithAnonymousUser
    void testGetUserPublicationWithAnonymous() throws Exception {
        mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testGetUserPublicationWithUser() throws Exception {
        mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-images"));
    }
}
