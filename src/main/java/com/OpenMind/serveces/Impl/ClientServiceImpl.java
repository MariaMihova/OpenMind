package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.ClientBindingModel;
import com.OpenMind.models.entitis.Client;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.viewModels.ClientViewModel;
import com.OpenMind.repositories.ClientRepository;
import com.OpenMind.serveces.ClientService;
import com.OpenMind.serveces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper,
                             UserService userService) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Cacheable("clients")
    @Override
    public List<ClientViewModel> findClientsBySpecialist(String name) {
        return clientRepository.findAllBySpecialist(name);
    }

    @Override
    public boolean addNewClient(ClientBindingModel clientBindingModel, Principal principal) {

        Client client = modelMapper.map(clientBindingModel, Client.class);


        try {
            clientRepository.save(client);
        }catch (Exception e){
            return false;
        }

        UserEntity user = userService.findByUsername(principal.getName());
        user.addClient(client);
        userService.updateUser(user);

        return true;
    }
}
