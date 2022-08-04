package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.AuthoritiesModel;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.serviceModels.RegisterServiceModel;
import com.OpenMind.models.viewModels.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  {
    boolean existsByUsername(String username);

    boolean registerNewUser(RegisterServiceModel registerServiceModel);

    UserEntity findByUsername(String name);

    List<UserViewModel> findAllByProfessionalField(Long id);

    UserViewModel findById(Long id);

    void updateUser(UserEntity user);

    List<UserViewModel> getAllByRole(Role role);

    void setAuthorities(AuthoritiesModel authoritiesModel);





}
