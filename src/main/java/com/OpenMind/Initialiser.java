package com.OpenMind;

import com.OpenMind.serveces.ProfessionalFieldService;
import com.OpenMind.serveces.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialiser implements CommandLineRunner {

    private final ProfessionalFieldService professionalFieldService;
    private final UserRoleService userRoleService;

    public Initialiser(ProfessionalFieldService professionalFieldService,
                       UserRoleService userRoleService) {
        this.professionalFieldService = professionalFieldService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {


        if(!professionalFieldService.doseFieldsExists()){
            professionalFieldService.initialiseFields();
        }

        if(!userRoleService.doseRoleExists()){
            userRoleService.initialiseRoles();
        }

    }
}
