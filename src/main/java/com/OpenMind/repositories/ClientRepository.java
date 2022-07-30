package com.OpenMind.repositories;

import com.OpenMind.models.entitis.Client;
import com.OpenMind.models.viewModels.ClientViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select new com.OpenMind.models.viewModels.ClientViewModel(c.id, c.initials, c.age, c.initialRequest) from UserEntity u join u.clients c where u.username= :username")
    List<ClientViewModel> findAllBySpecialist(@Param(value = "username") String username);


}
