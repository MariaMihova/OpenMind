package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
import com.OpenMind.serveces.ArticleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ProfessionalFieldRepository professionalFieldRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "TestPassword";
    private UserEntity user;
    private ProfessionalField field;
    private Contacts contacts;

    @BeforeEach
    public void setUp() {
         user = new UserEntity();
         contacts = new Contacts();
//        Picture picture = new Picture();

        contacts.setCountry("USA");
        contacts.setCity("Gotham");
        contacts.setPhoneNumber("0878505050");
        contacts.setEmail("test@gmeil.com");

        contactsRepository.save(contacts);

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
        user.setContacts(contacts);
        userRepository.save(user);

//        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(r -> {
//            return new SimpleGrantedAuthority("ROLE_".concat(r.getRole().name()));
//        }).collect(Collectors.toList());
//
//        Long userId = user.getId();

    }

//    @AfterEach
//    void tearDown() {
//
//        userRepository.deleteAll();
//        contactsRepository.deleteAll();
//        userRoleRepository.deleteAll();
//        professionalFieldRepository.deleteAll();
//
//    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addArticlePage() throws Exception {

        mockMvc.perform(get("/add-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-article"));


        userRepository.deleteAll();
        contactsRepository.deleteAll();
//        userRoleRepository.deleteAll();
//        professionalFieldRepository.deleteAll();


    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addArticleMethod() throws Exception {

        mockMvc.perform(post("/add-article")
                        .param("title", "NerdyArticleTitle")
                        .param("content", "qwertyuiopasdfghjklzxcvbnm200")
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        userRepository.deleteAll();
        contactsRepository.deleteAll();
        userRoleRepository.deleteAll();


    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void articlePage() throws Exception {

        Article article = initArticle();

        mockMvc.perform(get("/article/" + article.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("isOwner"))
                .andExpect(view().name("article"));


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
