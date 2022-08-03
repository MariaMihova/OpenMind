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
    void addArticlePage() throws Exception {

        mockMvc.perform(get("/add-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-article"));



    }

    @Test
    @WithMockUser()
    void addArticleMethod() throws Exception {

        mockMvc.perform(post("/add-article")
                        .param("title", "NerdyArticleTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));


    }

    @Test
    @WithMockUser()
    void articlePage() throws Exception {


        mockMvc.perform(get("/article/" + testArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("isOwner"))
                .andExpect(view().name("article"));


    }

    @Test
    @WithMockUser("testUser")
    void deleteArticle() throws Exception {


        mockMvc.perform(delete("/article/{id}", testArticle.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#articles"));

    }


    @Test
    @WithMockUser("testUser")
    void editArticlePage() throws Exception {

        mockMvc.perform(get("/article/{id}/edit", testArticle.getId())
                        .with(csrf()))
                .andExpect(model().attributeExists("article"))
                .andExpect(status().isOk())
                .andExpect(view().name("article-edit"));
    }


    @Test
    @WithMockUser("testUser")
    void editArticleMethod() throws Exception {


        mockMvc.perform(patch("/article/{id}/edit", testArticle.getId())
                        .param("title", "EditedTitle")
                        .param("content", testArticle.getContent())
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/" + testArticle.getId()));

    }





}
