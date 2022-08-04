package com.OpenMind.models.enums;

public enum MeetingType {

    ONLINE, IN_PERSON;

    @Override
    public String toString(){

        String result = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return result.replaceAll("_", " ");
    }
}
