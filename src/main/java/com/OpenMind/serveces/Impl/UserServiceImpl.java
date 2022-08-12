package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.AuthoritiesModel;
import com.OpenMind.models.entitis.Article;
import com.OpenMind.models.entitis.Picture;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.serviceModels.RegisterServiceModel;
import com.OpenMind.models.viewModels.PictureViewModel;
import com.OpenMind.models.viewModels.UserViewModel;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.serveces.UserRoleService;
import com.OpenMind.serveces.ProfessionalFieldService;
import com.OpenMind.serveces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final CustomUserDetailService customUserDetailService;
    private final ProfessionalFieldService professionalFieldService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, UserRoleService userRoleService,
                           CustomUserDetailService customUserDetailService,
                           ProfessionalFieldService professionalFieldService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.customUserDetailService = customUserDetailService;
        this.professionalFieldService = professionalFieldService;
    }




    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean registerNewUser(RegisterServiceModel registerServiceModel) {


        UserEntity user = modelMapper.map(registerServiceModel, UserEntity.class);
        user.setProfessionalField(professionalFieldService.findByFieldName(FieldName.valueOf(registerServiceModel.getProfessionalField())));
        user.setPassword(passwordEncoder.encode(registerServiceModel.getRowPassword()));
        user.setRegisteredAt(LocalDate.now());
        user.setAuthorities(userRoleService.getRole(userRepository.count() > 0 ? Role.USER : Role.ADMIN));

        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            return false;
        }

        UserDetails principal = customUserDetailService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, user.getPassword(), principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;

    }

    @Override
    public UserEntity findByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public List<UserViewModel> findAllByProfessionalField(Long id) {

        List<UserViewModel> users = userRepository.findAllByProfessionalFieldId(id).stream()
                .map(user -> modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());

        users.forEach( u -> {
            if(u.getPicture() == null){
               PictureViewModel picture = new PictureViewModel();
                picture.setTitle("No profile picture");
                picture.setUrl("images/defalt-user.png");
                u.setPicture(picture);
            }
        });
        return users;


    }

    @Override
    public UserViewModel findById(Long id) {
         UserEntity userEntity = userRepository.findById(id).orElse(null);

        return modelMapper.map(userEntity, UserViewModel.class);
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }


    @Override
    public List<UserViewModel> getAllByRole(Role role) {
        return userRepository.findAllByAuthoritiesRole(role)
                .stream()
                .map(u -> modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setAuthorities(AuthoritiesModel authoritiesModel) {
        UserEntity user = userRepository.findByUsername(authoritiesModel.getUsername()).orElse(null);
        if(user != null){
            user.setAuthorities(userRoleService.getRole(authoritiesModel.getRole()));
            userRepository.save(user);
        }
    }




}
