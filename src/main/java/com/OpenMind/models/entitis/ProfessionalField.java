package com.OpenMind.models.entitis;

import com.OpenMind.models.enums.FieldName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="professional_fields")
public class ProfessionalField extends BaseEntity{

    private FieldName fieldName;
    private String description;

    public ProfessionalField(){}

    public ProfessionalField(FieldName fieldName, String description) {
      this.setFieldName(fieldName);
       this.setDescription(description);
    }

    @Column(nullable = false)
    public FieldName getFieldName() {
        return fieldName;
    }

    public void setFieldName(FieldName fieldName) {
        this.fieldName = fieldName;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
