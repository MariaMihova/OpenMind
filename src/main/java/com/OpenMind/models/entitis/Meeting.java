package com.OpenMind.models.entitis;

import com.OpenMind.models.enums.MeetingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meetings")
public class Meeting extends BaseEntity{

    private String topic;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserEntity creator;
    private MeetingType type;
    private List<UserEntity> participants;


    public Meeting(){}

    @Column(nullable = false)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Column(nullable = false)
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Column(nullable = false)
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @ManyToOne(optional = false)
    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    @Enumerated(EnumType.STRING)
    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserEntity> participants) {
        this.participants = participants;
    }
}
