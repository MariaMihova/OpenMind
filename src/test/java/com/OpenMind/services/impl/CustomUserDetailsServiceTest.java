package com.OpenMind.services.impl;

import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.serveces.Impl.CustomUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private CustomUserDetailService toTest;

    @BeforeEach
    void setUp(){

        toTest = new CustomUserDetailService(mockUserRepository);
    }



    @Test
    void loadUserByUsernameTest_userExists(){

        //arrange
        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setUsername("TestUsername");
        testUserEntity.setPassword("TestPassword");
        testUserEntity.setFirstName("TestFirstName");
        testUserEntity.setLastName("TestLastName");
        testUserEntity.setAuthorities(Set.of(new UserRole(Role.USER),
                new UserRole(Role.ADMIN)));

        when(mockUserRepository.findByUsername(testUserEntity.getUsername()))
                .thenReturn(Optional.of(testUserEntity));

        //act
        UserDetails userDetails =
        toTest.loadUserByUsername(testUserEntity.getUsername());

        String expectedRoles = "ROLE_ADMIN, ROLE_USER";

        String actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
        //assert
        Assertions.assertEquals(testUserEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void loadUserByUsernameTest_userDoseNotExist(){

        Assertions.assertThrows(UsernameNotFoundException.class,() -> toTest.loadUserByUsername("not_existing_username"));

    }
}
