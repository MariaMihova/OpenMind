package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.ArticleRepository;
import com.OpenMind.repositories.ContactsRepository;
import com.OpenMind.repositories.PictureRepository;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.serveces.ArticleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private PictureRepository pictureRepository;

    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "TestPassword";
    private UserEntity testUser;

    @BeforeEach
    public void setUp(){
        UserEntity user = new UserEntity();
        Contacts contacts = new Contacts();
//        Picture picture = new Picture();

        contacts.setCountry("USA");
        contacts.setCity("Gotham");
        contacts.setPhoneNumber("0878505050");
        contacts.setEmail("test@gmeil.com");

        contactsRepository.save(contacts);

        UserRole userRole = new UserRole(Role.ADMIN);
        ProfessionalField field = new ProfessionalField();
        field.setFieldName(FieldName.PSYCHOLOGY);

        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setFirstName("Tset");
        user.setFirstName("Testov");
        user.setAuthorities(Set.of(userRole));
        user.setProfessionalField(field);
        user.setContacts(contacts);

        testUser = userRepository.save(user);

        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getRole().name()));
        }).collect(Collectors.toList());

        Long userId = user.getId();

    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }


    @Test
    @WithMockUser(username="admin", roles = {"ADMIN"})
    void addArticlePage() throws Exception {
        mockMvc.perform(get("/add-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("/add-article"));
    }
}
