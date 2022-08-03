package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.ArticleRepository;
import com.OpenMind.repositories.ProfessionalFieldRepository;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.repositories.UserRoleRepository;
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
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialWorkControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;
    //todo articles

    @BeforeEach
    public void setUp() {
        testUser = testUtils.testUserUser("TestUser");

    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();
    }

    @Test
    @WithMockUser("TestUser")
    void socialWorkPage() throws Exception {
        mockMvc.perform(get("/social-work"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialists"))
                .andExpect(model().attributeExists("socialArticles"))
                .andExpect(view().name("/social-work"));


    }



}
