package com.OpenMind.serveces;

import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;

import java.util.Set;

public interface UserRoleService {
    boolean doseRoleExists();

    void initialiseRoles();

    Set<UserRole> getRole(Role role);
}
