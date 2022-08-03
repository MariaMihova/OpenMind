package com.OpenMind.utils;

import com.OpenMind.models.entitis.*;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Gender;
import com.OpenMind.models.enums.MeetingType;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class TestUtils {

   private UserRepository userRepository;
   private UserRoleRepository userRoleRepository;
   private ProfessionalFieldRepository professionalFieldRepository;
   private ArticleRepository articleRepository;
   private ClientRepository clientRepository;
   private MeetingRepository meetingRepository;
   private ContactsRepository contactsRepository;

   public TestUtils(UserRepository userRepository, UserRoleRepository userRoleRepository,
                    ProfessionalFieldRepository professionalFieldRepository,
                    ArticleRepository articleRepository, ClientRepository clientRepository,
                    MeetingRepository meetingRepository, ContactsRepository contactsRepository) {
       this.userRepository = userRepository;
       this.userRoleRepository = userRoleRepository;
       this.professionalFieldRepository = professionalFieldRepository;
       this.articleRepository = articleRepository;
       this.clientRepository = clientRepository;
       this.meetingRepository = meetingRepository;
       this.contactsRepository = contactsRepository;
   }


   private void initRoles(){
       if(userRoleRepository.count() == 0){
           UserRole admin = new UserRole(Role.ADMIN);
           UserRole user = new UserRole(Role.USER);

           userRoleRepository.save(admin);
           userRoleRepository.save(user);
       }
   }

   private void initField(){
       if(professionalFieldRepository.count() == 0){
           ProfessionalField psychology = new ProfessionalField();
           psychology.setFieldName(FieldName.PSYCHOLOGY);

           ProfessionalField socialWork  = new ProfessionalField();
           socialWork.setFieldName(FieldName.SOCIAL_WORK);

           ProfessionalField speechTherapy = new ProfessionalField();
           speechTherapy.setFieldName(FieldName.SPEECH_THERAPY);

           professionalFieldRepository.save(psychology);
           professionalFieldRepository.save(socialWork);
           professionalFieldRepository.save(speechTherapy);
       }
   }

   public UserEntity testUserAdmin(String username){
       initField();
       initRoles();

       UserEntity admin = new UserEntity();
       admin.setUsername(username);
       admin.setPassword("adminpassword");
       admin.setFirstName("Admin");
       admin.setLastName("Adminov");
       admin.setRegisteredAt(LocalDate.now());
       admin.setProfessionalField(professionalFieldRepository.findTopByFieldName(FieldName.PSYCHOLOGY));
       admin.setAuthorities(userRoleRepository.findTopByRole(Role.ADMIN));
       userRepository.save(admin);
       return admin;
   }

    public UserEntity testUserUser(String username){
        initField();
        initRoles();

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("userpassword");
        user.setFirstName("User");
        user.setLastName("Userov");
        user.setRegisteredAt(LocalDate.now());
        user.setProfessionalField(professionalFieldRepository.findTopByFieldName(FieldName.PSYCHOLOGY));
        user.setAuthorities(userRoleRepository.findTopByRole(Role.USER));
        userRepository.save(user);
        return user;
    }

    public Article testArticle(UserEntity creator){
        initField();

       Article article = new Article();
       article.setTitle("TestArticle");
       article.setCreated(LocalDate.now());
       article.setProfessionalField(professionalFieldRepository.findTopByFieldName(FieldName.PSYCHOLOGY));
       article.setContent("The following readers’ answers to this central philosophical question" +
               " each win a random book." +
               "What’s the problem? Isn’t it enough that things are as they are? No, because we are sometimes deceived. " +
               "We need to tell the difference between hard ground and marsh that only looks hard. We need to know whether " +
               "something is a bear or only a child with a bearskin rug over its head. We have evolved to tell the real " +
               "from the false. Injure the brain and the victim may lose their sense of reality. When you have flu the " +
               "familiar world can seem unreal. You might as well ask “What is the nature of ‘upright’?”");
       article.setUser(creator);

       articleRepository.save(article);

       return article;
    }

    public Contacts testContacts(UserEntity creator){

        Contacts contacts = new Contacts();

        contacts.setCountry("USA");
        contacts.setCity("Gotham");
        contacts.setPhoneNumber("0878505050");
        contacts.setEmail("test@gmeil.com");
        contactsRepository.save(contacts);

        creator.setContacts(contacts);
        userRepository.save(creator);

        return contacts;
    }

    public Client testClient(UserEntity user){

       Client  client = new Client();
        client.setInitials("AJ");
        client.setAge(20);
        client.setInitialRequest("Clients initial request");
        client.setGender(Gender.MAN);

        clientRepository.save(client);
        user.setClients(Set.of(client));
        userRepository.save(user);
        return client;
    }

    public Meeting testMeeting(UserEntity creator){

       Meeting meeting = new Meeting();
        meeting.setTopic("Meeting topic");
        meeting.setStart( LocalDateTime.of(2023, 12, 12, 8, 0, 0));
        meeting.setEnd( LocalDateTime.of(2023, 12, 12, 9, 0, 0));
        meeting.setType(MeetingType.ONLINE);
        meeting.setCreator(creator);

        meetingRepository.save(meeting);

       return meeting;
    }

    public void clearDB(){
        articleRepository.deleteAll();
        meetingRepository.deleteAll();
        userRepository.deleteAll();
        contactsRepository.deleteAll();
        clientRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();
    }


}
