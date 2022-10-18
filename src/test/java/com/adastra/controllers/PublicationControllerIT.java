package com.adastra.controllers;
import com.adastra.models.Publication;
import com.adastra.models.User;
import com.adastra.repositories.PublicationRepository;
import com.adastra.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.attribute.UserPrincipal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PublicationControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataUtils testDataUtils;
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserDetailsService userDetailsService;

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
    void testAllPublicationsPageShown() throws Exception {
        mockMvc.perform(get("/publications/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("publications"));
    }

    @Test
    @WithAnonymousUser
    void testPublicationDetailsPageShown() throws Exception {
        mockMvc.perform(get("/publications/" + testUserPublication.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("details"));
    }

    @Test
    @WithAnonymousUser
    void testPublicationUploadPageNotShownForAnonymous() throws Exception {
        mockMvc.perform(get("/publications/upload"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testPublicationUploadPageShownForUser() throws Exception {
        mockMvc.perform(get("/publications/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    @WithAnonymousUser
    void testPublicationUploadForAnonymous() throws Exception {
        long initialPublications = publicationRepository.count();
        mockMvc.perform(post("/publications/upload")
                        .param("title", "title")
                        .param("image", "image")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(initialPublications, publicationRepository.count());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testPublicationUploadForUser() throws Exception {
        long initialPublications = publicationRepository.count();
        mockMvc.perform(post("/publications/upload")
                        .param("title", "title")
                        .param("image", "image")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(initialPublications + 1, publicationRepository.count());
    }

    @Test
    @WithAnonymousUser
    void testEditPageShowForAnonymous() throws Exception {
        mockMvc.perform(get("/publications/" + testUserPublication.getId() + "/edit"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPageShowForNotOwner() throws Exception {
//        mockMvc.perform(get("/publications/" + testAdminPublication.getId() + "/edit"))
//                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPageShowForOwner() throws Exception {
        mockMvc.perform(get("/publications/" + testUserPublication.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPageShowForAdmin() throws Exception {
        mockMvc.perform(get("/publications/" + testUserPublication.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPublicationForNotOwner() throws Exception {
        mockMvc.perform(put("/publications/" + testAdminPublication.getId())
                        .param("title", "editedT")
                        .param("image", "editedI")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Publication publication = publicationRepository.findById(testUserPublication.getId()).get();

        Assertions.assertEquals(testUserPublication.getTitle(), publication.getTitle());
        Assertions.assertEquals(testUserPublication.getImage(), publication.getImage());
    }

    @Test
    @WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPublicationForOwner() throws Exception {

        mockMvc.perform(put("/publications/" + testUserPublication.getId())
                        .param("title", "editedT")
                        .param("image", "editedI")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publications/" + testUserPublication.getId()));

        Publication publication = publicationRepository.findById(testUserPublication.getId()).get();

        Assertions.assertEquals("editedT", publication.getTitle());
        Assertions.assertEquals("editedI", publication.getImage());
    }

    @Test
    @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testEditPublicationForAdmin() throws Exception {
        mockMvc.perform(put("/publications/" + testUserPublication.getId())
                        .param("title", "editedT")
                        .param("image", "editedI")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publications/" + testUserPublication.getId()));

        Publication publication = publicationRepository.findById(testUserPublication.getId()).get();

        Assertions.assertEquals("editedT", publication.getTitle());
        Assertions.assertEquals("editedI", publication.getImage());
    }

    @Test
    @WithAnonymousUser
    void testEditPublicationForAnonymous() throws Exception {
        mockMvc.perform(put("/publications/" + testUserPublication.getId())
                        .param("title", "editedT")
                        .param("image", "editedI")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Publication publication = publicationRepository.findById(testUserPublication.getId()).get();

        Assertions.assertEquals(testUserPublication.getTitle(), publication.getTitle());
        Assertions.assertEquals(testUserPublication.getImage(), publication.getImage());
    }


}
