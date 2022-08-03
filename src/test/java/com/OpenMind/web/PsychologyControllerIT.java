package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
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

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PsychologyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;
    //todo articles


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
    void psychologyPage() throws Exception {
        mockMvc.perform(get("/psychology"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialists"))
                .andExpect(model().attributeExists("psychologyArticles"))
                .andExpect(view().name("psychology"));


    }


}
