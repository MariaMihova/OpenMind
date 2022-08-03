package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Gender;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
   private TestUtils testUtils;


    private UserEntity testUser;
    private Contacts contacts;
    private Article article;
    private Meeting meeting;
    private Client client;

    @BeforeEach
    public void setUp() {
        testUser = testUtils.testUserUser("TestUser");
        article = testUtils.testArticle(testUser);
        meeting = testUtils.testMeeting(testUser);
        contacts = testUtils.testContacts(testUser);
        client = testUtils.testClient(testUser);

    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();
    }

    @Test
    @WithMockUser("TestUser")
    void profilePage() throws Exception {

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userArticles"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attributeExists("meetings"))
                .andExpect(model().attributeExists("pictureViewModel"))
                .andExpect(model().attributeExists("contactViewModel"))
                .andExpect(view().name("profile"));


    }

    //todo test rest method

    @Test
    @WithMockUser("TestUser")
    void profileDetailsPage() throws Exception {
        UserEntity secondUser = testUtils.testUserUser("secondUser");
        mockMvc.perform(get("/profile-details/" + secondUser.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userViewDetails"))
                .andExpect(model().attributeExists("userArticles"))
                .andExpect(model().attributeExists("pictureViewModel"))
                .andExpect(model().attributeExists("contactViewModel"))
                .andExpect(view().name("profile-details"));

    }


}
