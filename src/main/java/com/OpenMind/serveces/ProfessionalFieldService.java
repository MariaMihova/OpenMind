package com.OpenMind.serveces;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.enums.FieldName;

public interface ProfessionalFieldService {


    boolean doseFieldsExists();

    void initialiseFields();

    ProfessionalField findByFieldName(FieldName field);

}
