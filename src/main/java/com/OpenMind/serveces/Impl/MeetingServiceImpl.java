package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.models.entitis.Meeting;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.viewModels.MeetingViewModel;
import com.OpenMind.repositories.MeetingRepository;
import com.OpenMind.serveces.UserService;
import com.OpenMind.serveces.MeetingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public MeetingServiceImpl(MeetingRepository meetingRepository, ModelMapper modelMapper, UserService userService) {
        this.meetingRepository = meetingRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public boolean addNewMeeting(MeetingBindingModel meetingBindingModel, Principal principal) {

        Meeting meeting = modelMapper.map(meetingBindingModel, Meeting.class);
        UserEntity creator = userService.findByUsername(principal.getName());

        meeting.setCreator(creator);

        //todo add participants

        try {
            meetingRepository.save(meeting);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<MeetingViewModel> findMeetingsByUsername(String name) {
        return meetingRepository.findAllByUsername(name);
    }
}
