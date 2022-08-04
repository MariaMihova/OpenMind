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

    public MeetingBindingModel(){}


    @NotNull
    @Size(min= 2)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @NotNull
    @Future()
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @NotNull
    @Future()
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @NotNull
    @ValidateString(acceptedValues = {"ONLINE", "IN_PERSON"})
    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    @AssertTrue(message = "Field `end` should be later than `start`")
    // Other rules can also be validated in other methods
    private boolean isEndAfterStart() {
        return start.isBefore(end);
    }
}
