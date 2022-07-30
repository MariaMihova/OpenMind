package com.OpenMind.repositories;

import com.OpenMind.models.entitis.Contacts;
import com.OpenMind.models.viewModels.ContactViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    @Query("select new com.OpenMind.models.viewModels.ContactViewModel(concat(c.city, ', ', c.country), c.phoneNumber, c.email) from UserEntity u join u.contacts c where u.username= :username")
    ContactViewModel findByUsername(@Param(value = "username") String username);
}
