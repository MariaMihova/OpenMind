package com.OpenMind.services.impl;

import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.UserRoleRepository;
import com.OpenMind.serveces.Impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {

    @Mock
    UserRoleRepository mockRoleRepository;

    private UserRoleServiceImpl toTest;

    @BeforeEach
    void setUp(){
        toTest = new UserRoleServiceImpl(mockRoleRepository);
    }


    @Test
    void doseRoleExists_Dose(){
        when(mockRoleRepository.count())
                .thenReturn(1L);

        Assertions.assertTrue(toTest.doseRoleExists());
    }

    @Test
    void doseRoleExists_DoseNot(){
        when(mockRoleRepository.count())
                .thenReturn(0L);

        Assertions.assertFalse(toTest.doseRoleExists());
    }

    //todo test initialiseRoles

    @Test
    void getRole_test(){

        Set<UserRole> expectedRoles = Set.of(new UserRole(Role.ADMIN));

        when(mockRoleRepository.findAllByRole(Role.ADMIN))
                .thenReturn(expectedRoles);

        Set<UserRole> actualRoles = toTest.getRole(Role.ADMIN);
        String expected = expectedRoles.stream().map(r -> r.getRole().name()).collect(Collectors.joining(", "));
        String actual = actualRoles.stream().map(r -> r.getRole().name()).collect(Collectors.joining(", "));

        Assertions.assertNotNull(actualRoles);
        Assertions.assertEquals(expectedRoles.size(), actualRoles.size());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRole_notFound(){

        Set<UserRole> actualRoles = toTest.getRole(Role.USER);
        Assertions.assertEquals(0, actualRoles.size());
    }
}
