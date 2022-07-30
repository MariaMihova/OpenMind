package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.ContactsBindingModel;
import com.OpenMind.models.entitis.Contacts;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.viewModels.ContactViewModel;
import com.OpenMind.repositories.ContactsRepository;
import com.OpenMind.serveces.ContactsService;
import com.OpenMind.serveces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ContactsServiceImpl(ContactsRepository contactsRepository, UserService userService, ModelMapper modelMapper) {
        this.contactsRepository = contactsRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean addNewContacts(ContactsBindingModel contactsBindingModel, Principal principal) {

        Contacts contacts = modelMapper.map(contactsBindingModel, Contacts.class);

        try {
            contactsRepository.save(contacts);

            UserEntity user = userService.findByUsername(principal.getName());
            user.setContacts(contacts);
            userService.updateUser(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public ContactViewModel findContactByUserName(String name) {
        ContactViewModel contactViewModel = contactsRepository.findByUsername(name);
        if(contactViewModel == null){
            return new ContactViewModel("N/A", "N/A", "N/A");
        }
        return contactViewModel;
    }
}
