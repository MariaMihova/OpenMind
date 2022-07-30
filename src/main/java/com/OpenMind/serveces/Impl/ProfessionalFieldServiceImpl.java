package com.OpenMind.serveces.Impl;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.repositories.ProfessionalFieldRepository;
import com.OpenMind.serveces.ProfessionalFieldService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProfessionalFieldServiceImpl implements ProfessionalFieldService {

    private final ProfessionalFieldRepository fieldRepository;


    public ProfessionalFieldServiceImpl(ProfessionalFieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public boolean doseFieldsExists() {
        return fieldRepository.count() > 0;
    }

    @Override
    public void initialiseFields() {

        Arrays.stream(FieldName.values())
                .forEach(fieldName -> fieldRepository.save
                        (new ProfessionalField(fieldName, String.format(
                                "Description for %s", fieldName.name()))));

    }

    @Override
    public ProfessionalField findByFieldName(FieldName field) {
        return fieldRepository.findByFieldName(field);
    }
}
