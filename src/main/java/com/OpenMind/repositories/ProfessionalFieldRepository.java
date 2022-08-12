package com.OpenMind.repositories;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.enums.FieldName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalFieldRepository extends JpaRepository<ProfessionalField, Long> {

    ProfessionalField findTopByFieldName(FieldName field);

}
