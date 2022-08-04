package com.OpenMind.web;


import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.enums.Role;
import com.OpenMind.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



;import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    private UserEntity testUser;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    void tearDown() {
        testUtils.clearDB();
    }

    @Test
    @WithMockUser(value = "Admin", roles = {"ADMIN"})
    void adminPageAdmin() throws Exception {
        UserEntity admin = testUtils.testUserAdmin("Admin");
        mockMvc.perform(get("/admin")
                        .with(csrf()))
                .andExpect(model().attributeExists("statView"))
                .andExpect(model().attributeExists("administrators"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser("testUser")
    void adminPageUse() throws Exception {
        UserEntity user = testUtils.testUserUser("testUser");
        mockMvc.perform(get("/admin")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void editArticlePageNotLoggedUser() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(value = "Admin", roles = {"ADMIN"})
    void setAuthoritiesPageAdmin() throws Exception {
        UserEntity admin = testUtils.testUserAdmin("Admin");
        mockMvc.perform(post("/set-authorities")
                        .param("username", "NewAdmin")
                        .param("role", String.valueOf(Role.ADMIN))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    @WithMockUser("User")
    void setAuthoritiesPageUser() throws Exception {
        UserEntity user = testUtils.testUserUser("User");
        mockMvc.perform(post("/set-authorities")
                        .param("username", "NewAdmin")
                        .param("role", String.valueOf(Role.ADMIN))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
