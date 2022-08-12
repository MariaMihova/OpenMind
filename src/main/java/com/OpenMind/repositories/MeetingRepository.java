package com.OpenMind.repositories;

import com.OpenMind.models.entitis.Meeting;
import com.OpenMind.models.viewModels.MeetingViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {


    List<Meeting> findAllByCreatorUsernameOrderByStart(String name);

    List<Meeting> findTop5ByOrderByStart();

}
