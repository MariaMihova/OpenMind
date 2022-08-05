package com.OpenMind.models.viewModels;

import com.OpenMind.models.entitis.Meeting;
import com.OpenMind.models.enums.MeetingType;
import org.aspectj.lang.annotation.After;

import java.time.LocalDateTime;

public class MeetingViewModel {

    private Long id;
    private String topic;
    private String start;
    private String end;
    private String type;

    public MeetingViewModel(){}

    public MeetingViewModel(Long id, String topic, String start, String end, String type) {
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


    public String getStart() {
        return start;
    }

    public void setStart(String start) {


        this.start = start;
    }

    public String getEnd() {
        return end;
    }


    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
