package com.OpenMind.web;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Gender;
import com.OpenMind.models.enums.MeetingType;
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
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerIT {
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

    @Autowired
    private ClientRepository clientRepository;


    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "TestPassword";
    private UserEntity user;
    private ProfessionalField field;
    private Contacts contacts;
    private Article article;
    private Meeting meeting;
    private Client client;

    @BeforeEach
    public void setUp() {

        user = new UserEntity();
        contacts = new Contacts();

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

        article = new Article();
        article.setTitle("ArticleTitle");
        article.setContent("qwertyuiopasdfghjklzxcvbnm");
        article.setCreated(LocalDate.now());
        article.setProfessionalField(field);
        article.setUser(user);

        articleRepository.save(article);

        meeting = new Meeting();
        meeting.setTopic("Meeting topic");
        meeting.setStart( LocalDateTime.of(2023, 12, 12, 8, 0, 0));
        meeting.setEnd( LocalDateTime.of(2023, 12, 12, 9, 0, 0));
        meeting.setType(MeetingType.ONLINE);
        meeting.setCreator(user);

        meetingRepository.save(meeting);

        client = new Client();
        client.setInitials("AJ");
        client.setAge(20);
        client.setInitialRequest("Clients initial request, a very long one");
        client.setGender(Gender.MAN);

        clientRepository.save(client);
        user.setClients(Set.of(client));

    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
        meetingRepository.deleteAll();
        userRepository.deleteAll();
        contactsRepository.deleteAll();
        clientRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();


    }

    @Test
    @WithMockUser(username = USERNAME)
    void profilePage() throws Exception {

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userArticles"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attributeExists("meetings"))
                .andExpect(model().attributeExists("pictureViewModel"))
                .andExpect(model().attributeExists("contactViewModel"))
                .andExpect(view().name("profile"));


    }

    //todo test rest method

    @Test
    @WithMockUser(username = USERNAME)
    void profileDetailsPage() throws Exception {
        UserEntity secondUser = initSecondUser();
        mockMvc.perform(get("/profile-details/" + secondUser.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userViewDetails"))
                .andExpect(model().attributeExists("userArticles"))
                .andExpect(model().attributeExists("pictureViewModel"))
                .andExpect(model().attributeExists("contactViewModel"))
                .andExpect(view().name("profile-details"));

    }

    private UserEntity initSecondUser(){
        UserEntity secondUser = new UserEntity();

        Contacts contacts2 = new Contacts();
        contacts2 = new Contacts();

        contacts2.setCountry("USA");
        contacts2.setCity("New York");
        contacts2.setPhoneNumber("0878010101");
        contacts2.setEmail("second@gmeil.com");
        contactsRepository.save(contacts2);


        UserRole userRole = new UserRole(Role.USER);
        userRoleRepository.save(userRole);

        ProfessionalField field2 = new ProfessionalField();
        field2.setFieldName(FieldName.PSYCHOLOGY);
        field2.setDescription("Description for field PSYCHOLOGY");
        professionalFieldRepository.save(field2);

        secondUser.setUsername("SecondUser");
        secondUser.setPassword("secondPassword");
        secondUser.setFirstName("Second");
        secondUser.setLastName("Secondov");
        secondUser.setAuthorities(Set.of(userRole));
        secondUser.setProfessionalField(field2);
        secondUser.setContacts(contacts2);
        userRepository.save(secondUser);

        Article article2 = new Article();
        article2.setTitle("SecondTitle");
        article2.setContent("qwertyuiopasdfghjklzxcvbnm");
        article2.setCreated(LocalDate.now());
        article2.setProfessionalField(field2);
        article2.setUser(secondUser);

        articleRepository.save(article2);

        return secondUser;
    }

}
