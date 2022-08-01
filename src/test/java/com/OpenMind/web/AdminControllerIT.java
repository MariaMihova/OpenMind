package com.OpenMind.web;

import com.OpenMind.models.entitis.Contacts;
import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.ProfessionalFieldRepository;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ProfessionalFieldRepository professionalFieldRepository;

    private final String USERNAME = "testUser";
    private final String PASSWORD = "testPassword";

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

        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getRole().name()));
        }).collect(Collectors.toList());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        professionalFieldRepository.deleteAll();
    }

//    @Test
//    @WithMockUser(username = USERNAME, )
//    void adminPag() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("statView"))
//                .andExpect(model().attributeExists("administrators"))
//                .andExpect(view().name("admin"));
//    }

}
