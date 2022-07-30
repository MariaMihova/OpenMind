package com.OpenMind.serveces.Impl;

import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.UserRoleRepository;
import com.OpenMind.serveces.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public boolean doseRoleExists() {
        return userRoleRepository.count() > 0;
    }

    @Override
    public void initialiseRoles() {
        Arrays.stream(Role.values())
                .forEach(role -> userRoleRepository.save
                        (new UserRole(role)));
    }

    @Override
    public Set<UserRole> getRole(Role role) {
        return userRoleRepository.findAllByRole(role);
    }
}
