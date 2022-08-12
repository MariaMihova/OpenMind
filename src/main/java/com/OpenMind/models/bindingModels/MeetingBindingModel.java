package com.OpenMind.models.bindingModels;

import com.OpenMind.models.enums.MeetingType;
import com.OpenMind.utils.ValidateString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MeetingBindingModel {

    private String topic;
    private LocalDateTime start;
    private LocalDateTime end;
    private MeetingType type;
    private final boolean isEndValid = this.isEndAfterStart();

    public MeetingBindingModel(){}


    @NotNull
    @Size(min= 2, message = "The topic of the meeting must be 2 or more symbols.")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @NotNull(message = "Place, select future date!")
    @Future(message = "The start of the meeting can not be in the past.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @NotNull(message = "Place, select future date!")
    @Future(message = "The end of the meeting can not be in the past.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @NotNull(message = "Please, select meeting type!")
    @ValidateString(acceptedValues = {"ONLINE", "IN_PERSON"})
    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    @AssertTrue(message = "Field `end` should be later than `start`")
    private boolean isEndAfterStart() {
        if(this.start == null || this.end == null) return false;
        return start.isBefore(end);
    }
}
