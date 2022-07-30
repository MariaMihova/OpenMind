package com.OpenMind.repositories;

import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Set<UserRole> findAllByRole(Role role);
}
