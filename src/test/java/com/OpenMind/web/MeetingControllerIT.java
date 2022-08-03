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
public class MeetingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
   private TestUtils testUtils;

    private UserEntity testUser;


    @BeforeEach
    public void setUp() {
        testUser = testUtils.testUserUser("TestUsername");

    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();
    }


    @Test
    @WithMockUser("TestUsername")
    void addMeetingPageTest() throws Exception {

        mockMvc.perform(get("/add-meeting"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-meeting"));

    }


    @Test
    @WithMockUser("TestUsername")
    void addMeetingMethod() throws Exception {

        mockMvc.perform(post("/add-meeting")
                        .param("topic", "Meeting topic")
                        .param("start", "2023-12-12T08:00:00")
                        .param("end", "2023-12-12T09:00:00")
                        .param("type", "ONLINE")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#meetings"));


    }
}
