package com.metacoding.springrocketdanv2.job.techstack;

public enum JobStatusEnum {
    OPEN("OPEN"),
    CLOSE("CLOSE");

    public String value;

    JobStatusEnum(String value) {
        this.value = value;
    }
}
