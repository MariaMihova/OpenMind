package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.PictureBindingModel;
import com.OpenMind.models.viewModels.PictureViewModel;

import java.io.IOException;
import java.security.Principal;

public interface PictureService {
    boolean createPicture(PictureBindingModel pictureBindingModel, Principal principal) throws IOException;

    void delete(String public_id);

    PictureViewModel getProfilePicture(String username);

    boolean editPicture(PictureBindingModel pictureBindingModel, Principal principal) throws IOException;


}
