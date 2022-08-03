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
public class ContactsControllerIT {

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
    @WithMockUser("TestUser")
    void addContactsPage() throws Exception {

        mockMvc.perform(get("/add-contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add-contacts"));
    }

    @Test
    @WithMockUser("TestUser")
    void addContactsMethod() throws Exception {

        mockMvc.perform(post("/add-contacts")
                        .param("country", "USA")
                        .param("city", "Gotham")
                        .param("phoneNumber", "0878010101")
                        .param("email", "test@gmail.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-picture"));


    }

}
