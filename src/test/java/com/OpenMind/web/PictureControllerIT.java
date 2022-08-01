package com.OpenMind.web;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
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
    private PictureRepository pictureRepository;

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
        pictureRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();

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
