package com.metacoding.springrocketdanv2.user;

public enum UserTypeEnum {
    COMPANY("company"),
    USER("user");

    public String value;

    UserTypeEnum(String value) {
        this.value = value;
    }
}
