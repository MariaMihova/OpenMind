package com.OpenMind.web;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
import com.OpenMind.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PictureControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;


    @BeforeEach
    public void setUp() {

        testUser = testUtils.testUserUser("TestUser");

    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();

    }

    @Test
    @WithMockUser
    void addPicturePageTest() throws Exception {

        mockMvc.perform(get("/add-picture"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add-picture"));

    }

    //todo ask how to test add picture

//    @Test
//    @WithMockUser(username = USERNAME)
//    void addArticleMethod() throws Exception {
//
//        mockMvc.perform(post("/add-picture")
//                        .param("title", "Picture title")
//                        .param("picture", "\"C:\\Users\\mivan\\OneDrive\\Desktop\\posters\\Harley.jpg\"")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/home"));
//
//
//    }

    @Test
    @WithMockUser
    void editPicturePageTest() throws Exception {

        mockMvc.perform(get("/edit-picture"))
                .andExpect(status().isOk())
                .andExpect(view().name("/edit-picture"));

    }

    //todo edit picture post method


}
