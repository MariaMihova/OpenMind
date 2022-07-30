package com.OpenMind.services.impl;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.Role;
import com.OpenMind.repositories.ProfessionalFieldRepository;
import com.OpenMind.serveces.Impl.ProfessionalFieldServiceImpl;
import com.OpenMind.serveces.Impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessionalFieldServiceImplTest {

    @Mock
    ProfessionalFieldRepository mockRepository;

    private ProfessionalFieldServiceImpl toTest;

    @BeforeEach
    void setUp(){
        toTest = new ProfessionalFieldServiceImpl(mockRepository);
    }

    @Test
    void doseFieldsExists_Dose(){
        when(mockRepository.count())
                .thenReturn(1L);

        Assertions.assertTrue(toTest.doseFieldsExists());
    }

    @Test
    void doseFieldsExists_DoseNot(){
        when(mockRepository.count())
                .thenReturn(0L);

        Assertions.assertFalse(toTest.doseFieldsExists());
    }

    //todo test initialiseFields

    @Test
    void findByFieldName_fond(){

        ProfessionalField expectedField = new ProfessionalField();
        expectedField.setFieldName(FieldName.PSYCHOLOGY);

        when(mockRepository.findByFieldName(FieldName.PSYCHOLOGY))
                .thenReturn(expectedField);

       ProfessionalField actualField = toTest.findByFieldName(FieldName.PSYCHOLOGY);

        Assertions.assertNotNull(actualField);
        Assertions.assertEquals(expectedField.getFieldName().name(), actualField.getFieldName().name());
    }

    @Test
    void getRole_notFound(){

        ProfessionalField actualField = toTest.findByFieldName(FieldName.PSYCHOLOGY);
        Assertions.assertNull(actualField);
    }
}
