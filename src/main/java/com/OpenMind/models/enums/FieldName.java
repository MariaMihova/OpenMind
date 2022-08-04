package com.OpenMind.models.enums;

public enum FieldName {

    PSYCHOLOGY, SOCIAL_WORK, SPEECH_THERAPY;

    @Override
    public String toString(){

        String result = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return result.replaceAll("_", " ");
    }
}
