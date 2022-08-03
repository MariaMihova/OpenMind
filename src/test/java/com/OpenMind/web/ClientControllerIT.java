package com.OpenMind.web;

import com.OpenMind.models.entitis.Client;
import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Gender;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.ClientRepository;
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

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIT {
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
    void addClientPageTest() throws Exception {

        mockMvc.perform(get("/add-client"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add-client"));

    }

    @Test
    @WithMockUser("TestUser")
    void addClientMethod() throws Exception {

        mockMvc.perform(post("/add-client")
                        .param("initials", "AJ")
                        .param("age", "20")
                        .param("initialRequest", "the clients initial requestthe clients initial requestthe clients initial request")
                        .param("gender", "MAN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));


    }

}
