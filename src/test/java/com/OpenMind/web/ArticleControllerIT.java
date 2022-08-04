package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
import com.OpenMind.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;
    private Article testArticle;



    @BeforeEach
    public void setUp() {
        testUser = testUtils.testUserUser("testUser");
        testArticle = testUtils.testArticle(testUser);


    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();
    }


    @Test
    @WithMockUser()
    void addArticlePageLoggedUser() throws Exception {

        mockMvc.perform(get("/add-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-article"));



    }

    @Test
    void addArticlePageNotLoggedUser() throws Exception {

        mockMvc.perform(get("/add-article"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

    @Test
    @WithMockUser()
    void addArticleMethodLoggedUser() throws Exception {

        mockMvc.perform(post("/add-article")
                        .param("title", "NerdyArticleTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }



    @Test
    void addArticleMethodNotLoggedUser() throws Exception {

        mockMvc.perform(post("/add-article"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser()
    void articlePageLoggedUser() throws Exception {
        mockMvc.perform(get("/article/" + testArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("isOwner"))
                .andExpect(view().name("article"));

    }

    @Test
    void articlePageNotLoggedUser() throws Exception {

        mockMvc.perform(get("/article/" + testArticle.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }



    @Test
    @WithMockUser("testUser")
    void deleteArticleOwner() throws Exception {
        mockMvc.perform(delete("/article/{id}", testArticle.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#articles"));

    }

    @Test
    @WithMockUser("NotOwner")
    void deleteArticleNorOwner() throws Exception {
        UserEntity notOwner = testUtils.testUserUser("NotOwner");
        mockMvc.perform(delete("/article/{id}", testArticle.getId())
                        .with(csrf()))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser("Admin")
    void deleteArticleAdmin() throws Exception {
        UserEntity admin = testUtils.testUserAdmin("Admin");
        mockMvc.perform(delete("/article/{id}", testArticle.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#articles"));

    }

    @Test
    void deleteArticleNotLoggedUser() throws Exception {
        mockMvc.perform(delete("/article/{id}", testArticle.getId()))
                .andExpect(status().isForbidden());

    }



    @Test
    @WithMockUser("testUser")
    void editArticlePageOwner() throws Exception {
        mockMvc.perform(get("/article/{id}/edit", testArticle.getId())
                        .with(csrf()))
                .andExpect(model().attributeExists("article"))
                .andExpect(status().isOk())
                .andExpect(view().name("article-edit"));
    }

    @Test
    @WithMockUser("NotOwner")
    void editArticlePageNotOwner() throws Exception {
        UserEntity notOwner = testUtils.testUserUser("NotOwner");
        mockMvc.perform(get("/article/{id}/edit", testArticle.getId())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("Admin")
    void editArticlePageAdmin() throws Exception {
        UserEntity admin = testUtils.testUserAdmin("Admin");
        mockMvc.perform(get("/article/{id}/edit", testArticle.getId())
                        .with(csrf()))
                .andExpect(model().attributeExists("article"))
                .andExpect(status().isOk())
                .andExpect(view().name("article-edit"));
    }

    @Test
    void editArticlePageNotLoggedUser() throws Exception {
        mockMvc.perform(get("/article/{id}/edit", testArticle.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser("testUser")
    void editArticleMethodOwner() throws Exception {

        mockMvc.perform(patch("/article/{id}/edit", testArticle.getId())
                        .param("title", "EditedTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/" + testArticle.getId()));

    }

    @Test
    @WithMockUser("NotOwner")
    void editArticleMethodNotOwner() throws Exception {
        UserEntity notOwner = testUtils.testUserUser("NotOwner");
        mockMvc.perform(patch("/article/{id}/edit", testArticle.getId())
                        .param("title", "EditedTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser("Admin")
    void editArticleMethodAdmin() throws Exception {
        UserEntity admin = testUtils.testUserAdmin("Admin");
        mockMvc.perform(patch("/article/{id}/edit", testArticle.getId())
                        .param("title", "EditedTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/" + testArticle.getId()));

    }



    @Test
    void editArticleMethodNotLoggedUser() throws Exception {
        mockMvc.perform(patch("/article/{id}/edit", testArticle.getId())
                        .param("title", "EditedTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY"))
                .andExpect(status().isForbidden());

    }



}
