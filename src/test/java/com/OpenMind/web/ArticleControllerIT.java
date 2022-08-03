package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Autowired
    private MeetingRepository meetingRepository;


    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "TestPassword";
    private UserEntity user;
    private ProfessionalField field;
    private Contacts contacts;
    private  Article article;
    private static final String ARTICLE_CONTENT = "The following readers’ answers to this central philosophical question" +
            " each win a random book." +
            "What’s the problem? Isn’t it enough that things are as they are? No, because we are sometimes deceived. " +
            "We need to tell the difference between hard ground and marsh that only looks hard. We need to know whether " +
            "something is a bear or only a child with a bearskin rug over its head. We have evolved to tell the real " +
            "from the false. Injure the brain and the victim may lose their sense of reality. When you have flu the " +
            "familiar world can seem unreal. You might as well ask “What is the nature of ‘upright’?”";

    @BeforeEach
    public void setUp() {

//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         user = new UserEntity();
         contacts = new Contacts();

        contacts.setCountry("USA");
        contacts.setCity("Gotham");
        contacts.setPhoneNumber("0878505050");
        contacts.setEmail("test@gmeil.com");

        contactsRepository.save(contacts);

        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        userRoleRepository.save(userRole);

        field = new ProfessionalField();
        field.setFieldName(FieldName.PSYCHOLOGY);
        field.setDescription("Description for field PSYCHOLOGY");
        professionalFieldRepository.save(field);

        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setFirstName("Tset");
        user.setLastName("Testov");
        user.setAuthorities(userRoleRepository.findTopByRole(Role.ADMIN));
        user.setProfessionalField(field);
        user.setContacts(contacts);
        userRepository.save(user);

        article = new Article();
        article.setTitle("ArticleTitle");
        article.setContent(ARTICLE_CONTENT);
        article.setCreated(LocalDate.now());
        article.setProfessionalField(field);
        article.setUser(user);

        articleRepository.save(article);

    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
        meetingRepository.deleteAll();
        userRepository.deleteAll();
        contactsRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();


    }


    @Test
    @WithMockUser()
    void addArticlePage() throws Exception {

        mockMvc.perform(get("/add-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-article"));



    }

    @Test
    @WithMockUser()
    void addArticleMethod() throws Exception {

        mockMvc.perform(post("/add-article")
                        .param("title", "NerdyArticleTitle")
                        .param("content", ARTICLE_CONTENT)
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));


    }

    @Test
    @WithMockUser()
    void articlePage() throws Exception {


        mockMvc.perform(get("/article/" + article.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("isOwner"))
                .andExpect(view().name("article"));


    }

    @Test
    @WithMockUser(username = USERNAME)
    void deleteArticle() throws Exception {


        mockMvc.perform(delete("/article/{id}", article.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile#articles"));

    }


    @Test
    @WithMockUser(USERNAME)
    void editArticlePage() throws Exception {

        mockMvc.perform(get("/article/{id}/edit", article.getId())
                        .with(csrf()))
                .andExpect(model().attributeExists("article"))
                .andExpect(status().isOk())
                .andExpect(view().name("article-edit"));
    }


    @Test
    @WithMockUser(username = USERNAME)
    void editArticleMethod() throws Exception {


        mockMvc.perform(patch("/article/{id}/edit", article.getId())
                        .param("title", "EditedTitle")
                        .param("content", ARTICLE_CONTENT)
                        .param("professionalField", "PSYCHOLOGY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/" + article.getId()));

    }





}
