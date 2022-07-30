package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.ContactsBindingModel;
import com.OpenMind.models.viewModels.ContactViewModel;

import java.security.Principal;

public interface ContactsService {
    boolean addNewContacts(ContactsBindingModel contactsBindingModel, Principal principal);


    ContactViewModel findContactByUserName(String name);

}
