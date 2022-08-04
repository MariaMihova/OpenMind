package com.OpenMind.web;


import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.MeetingType;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;
    private Article testArticle;
    private Meeting testMeeting;


    @BeforeEach
    public void setUp() {
        testUser = testUtils.testUserUser("TestUser");
        testArticle = testUtils.testArticle(testUser);
        testMeeting = testUtils.testMeeting(testUser);

    }

    @AfterEach
    void tearDown() {

        testUtils.clearDB();

    }



    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"));
    }

    @Test
    @WithMockUser("TestUser")
    void testHomePageLoggedUser() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("meetings"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testHomePageNotLoggedUser() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }




}
