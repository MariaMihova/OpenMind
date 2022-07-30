package com.OpenMind.models.viewModels;

import com.OpenMind.models.entitis.Meeting;
import com.OpenMind.models.enums.MeetingType;

import java.time.LocalDateTime;

public class MeetingViewModel {

    private Long id;
    private String topic;
    private LocalDateTime start;
    private LocalDateTime end;
    private MeetingType type;

    public MeetingViewModel(){}

    public MeetingViewModel(Long id, String topic, LocalDateTime start, LocalDateTime end, MeetingType type) {
        this.id = id;
        this.topic = topic;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }
}
