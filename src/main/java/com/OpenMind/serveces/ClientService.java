package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.ClientBindingModel;
import com.OpenMind.models.viewModels.ClientViewModel;

import java.security.Principal;
import java.util.List;

public interface ClientService {
    List<ClientViewModel> findClientsBySpecialist(String name);

    boolean addNewClient(ClientBindingModel clientBindingModel, Principal principal);

}

