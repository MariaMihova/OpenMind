package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
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
    private ArticleRepository articleRepository;

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
        articleRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();
    }

    @Test
    @WithMockUser()
    void psychologyPage() throws Exception {
        mockMvc.perform(get("/psychology"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("specialists"))
                .andExpect(model().attributeExists("psychologyArticles"))
                .andExpect(view().name("psychology"));


    }



    private Article initArticle(){

        Article article = new Article();
        article.setTitle("ArticleTitle");
        article.setContent("qwertyuiopasdfghjklzxcvbnm");
        article.setCreated(LocalDate.now());
        article.setProfessionalField(field);
        article.setUser(user);

        articleRepository.save(article);
        return article;
    }


}
