package com.OpenMind.repositories;

import com.OpenMind.models.entitis.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    void deleteByPublicId(String public_id);

    Picture findFirstByUserUsername(String username);

}
