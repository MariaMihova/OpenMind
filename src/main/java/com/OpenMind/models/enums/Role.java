package com.OpenMind.models.enums;

public enum Role {
    ADMIN, USER;
    @Override
    public String toString(){

        String result = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return result.replaceAll("_", " ");
    }
}
