package com.OpenMind.serveces;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.models.viewModels.MeetingViewModel;

import java.security.Principal;
import java.util.List;

public interface MeetingService {
    boolean addNewMeeting(MeetingBindingModel meetingBindingModel, Principal principal);

    List<MeetingViewModel> findMeetingsByUsername(String name);

}
