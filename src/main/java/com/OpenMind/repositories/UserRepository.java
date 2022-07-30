package com.OpenMind.repositories;

import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.viewModels.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);


    List<UserEntity> findAllByProfessionalFieldId(Long id);

//    @Query("select new com.OpenMind.models.viewModels.UserViewModel(u.id,  u.firstName, u.lastName,u.username) from UserEntity u join u.authorities a where a.role like :role")

    List<UserEntity> findAllByAuthoritiesRole(Role role);


}
