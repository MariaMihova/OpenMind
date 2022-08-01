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
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessionalFieldRepository professionalFieldRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "TestPassword";
    private UserEntity user;
    private ProfessionalField field;


    @BeforeEach
    public void setUp() {

        user = new UserEntity();


        UserRole userRole = new UserRole(Role.ADMIN);
        userRoleRepository.save(userRole);

        field = new ProfessionalField();
        field.setFieldName(FieldName.PSYCHOLOGY);
        field.setDescription("Description for field PSYCHOLOGY");
        professionalFieldRepository.save(field);

        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setFirstName("Tset");
        user.setLastName("Testov");
        user.setAuthorities(Set.of(userRole));
        user.setProfessionalField(field);
        userRepository.save(user);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        clientRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();


    }

    @Test
    @WithMockUser
    void addClientPageTest() throws Exception {

        mockMvc.perform(get("/add-client"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add-client"));

    }

    @Test
    @WithMockUser(username = USERNAME)
    void addClientMethod() throws Exception {

        mockMvc.perform(post("/add-client")
                        .param("initials", "AJ")
                        .param("age", "20")
                        .param("initialRequest", "the clients initial requestthe clients initial requestthe clients initial request")
                        .param("gender", "MAN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#clients"));


    }




    private Client initArticle(){

        Client client = new Client();
        client.setInitials("A.J.");
        client.setInitialRequest("the clients initial request");
        client.setAge(20);
        client.setGender(Gender.MAN);

        clientRepository.save(client);
        return client;

    }
}
