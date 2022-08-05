package com.OpenMind.services.impl;


import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.viewModels.UserViewModel;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.serveces.Impl.CustomUserDetailService;
import com.OpenMind.serveces.Impl.UserServiceImpl;
import com.OpenMind.serveces.ProfessionalFieldService;
import com.OpenMind.serveces.UserRoleService;
import com.OpenMind.serveces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ModelMapper mockMapper;
    @Mock
    private PasswordEncoder mockEncoder;
    @Mock
    private UserRoleService mockUserRoleService;
    @Mock
    private CustomUserDetailService mockCustomUserDetailService;
    @Mock
    private ProfessionalFieldService mockProfessionalFieldService;

    private UserService toTest;
    private  UserEntity testUserEntity;
    private UserEntity secondEntity;
    private UserViewModel testViewModel;

    @BeforeEach
    void setUp(){
        toTest = new UserServiceImpl(mockUserRepository, mockMapper, mockEncoder,
                mockUserRoleService, mockCustomUserDetailService, mockProfessionalFieldService);

        testUserEntity = new UserEntity();
        testUserEntity.setId(1L);
        testUserEntity.setUsername("TestUsername");
        testUserEntity.setPassword("TestPassword");
        testUserEntity.setFirstName("TestFirstName");
        testUserEntity.setLastName("TestLastName");
        testUserEntity.setRegisteredAt(LocalDate.now());
        testUserEntity.setAuthorities(Set.of(new UserRole(Role.USER),
                new UserRole(Role.ADMIN)));

    }

    @Test
    void existsByUsername_UserExists(){

        when(mockUserRepository.existsByUsername(testUserEntity.getUsername()))
                .thenReturn(true);

        Assertions.assertTrue(toTest.existsByUsername(testUserEntity.getUsername()));
    }

    @Test
    void existsByUsername_UserDoNotExists(){
        Assertions.assertFalse(toTest.existsByUsername("nonExistingUsername"));
    }

    //todo test register new user


    @Test
    void findByUsername_found(){

        when(mockUserRepository.findByUsername(testUserEntity.getUsername()))
                .thenReturn(Optional.of(testUserEntity));

        UserEntity actualEntity = toTest.findByUsername(testUserEntity.getUsername());

        String actualRoles = actualEntity.getAuthorities()
                .stream()
                .map(a -> a.getRole().name())
                .sorted()
                .collect(Collectors.joining(", "));
        //assert

        Assertions.assertNotNull(actualEntity);
        assertTestEntity(actualEntity, actualRoles);
    }

    @Test
    void findByUsername_notFound(){

        UserEntity actualEntity = toTest.findByUsername("nonExistingUsername");
       Assertions.assertNull(actualEntity);
    }

    @Test
    void findById_found(){
        when(mockUserRepository.findById(testUserEntity.getId()))
                .thenReturn(Optional.of(testUserEntity));
        when(mockMapper.map(testUserEntity, UserViewModel.class))
                .thenReturn(new UserViewModel(
                        testUserEntity.getId(),
                        testUserEntity.getFirstName(),
                        testUserEntity.getLastName(),
                        testUserEntity.getUsername(),
                        testUserEntity.getRegisteredAt(),
                        "Psychology"));

        UserViewModel actualView = toTest.findById(testUserEntity.getId());

        Assertions.assertNotNull(actualView);
        assertTestViewEntity(actualView);


    }

    @Test
    void findById_NotFound(){

        UserViewModel actualEntity = toTest.findById(20L);
        Assertions.assertNull(actualEntity);
    }


    //todo test updateUser

    @Test
    void getAllByRole_foundByAdmin(){


        List<UserEntity> expectedList = new ArrayList<>();
        expectedList.add(testUserEntity);
        String expectedRoleName = Role.ADMIN.name();

        when(mockUserRepository.findAllByAuthoritiesRole(Role.ADMIN))
                .thenReturn(expectedList);
        when(mockMapper.map(testUserEntity, UserViewModel.class))
                .thenReturn(new UserViewModel(
                        testUserEntity.getId(),
                        testUserEntity.getFirstName(),
                        testUserEntity.getLastName(),
                        testUserEntity.getUsername(),
                        testUserEntity.getRegisteredAt(),
                        "Psychology"));

        List<UserViewModel> actualList = toTest.getAllByRole(Role.ADMIN);

        Assertions.assertNotNull(actualList);
        actualList.forEach(this::assertTestViewEntity);

    }


    @Test
    void getAllByRole_notFoundByAdmin(){
        initSecondEntity();
        List<UserEntity> expectedList = new ArrayList<>();
        expectedList.add(secondEntity);

        List<UserViewModel> actualList = toTest.getAllByRole(Role.ADMIN);

        Assertions.assertEquals(0, actualList.size());
    }

    //todo test setUserAuthorities


    private void assertTestEntity(UserEntity actualEntity,  String actualRoles){
        String expectedRoles = "ADMIN, USER";
        Assertions.assertEquals(testUserEntity.getUsername(), actualEntity.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), actualEntity.getPassword());
        Assertions.assertEquals(testUserEntity.getFirstName(), actualEntity.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), actualEntity.getLastName());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    private void initSecondEntity(){
        secondEntity = new UserEntity();
        secondEntity.setId(2L);
        secondEntity.setUsername("SecondUsername");
        secondEntity.setPassword("SecondPassword");
        secondEntity.setFirstName("SecondFirstName");
        secondEntity.setLastName("SecondLastName");
        secondEntity.setAuthorities(Set.of(new UserRole(Role.USER)));
    }



    private void assertTestViewEntity(UserViewModel actualEntity) {

        Assertions.assertEquals(testUserEntity.getId(), actualEntity.getId());
        Assertions.assertEquals(testUserEntity.getUsername(), actualEntity.getUsername());
        Assertions.assertEquals(testUserEntity.getFirstName(), actualEntity.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), actualEntity.getLastName());

    }


}
