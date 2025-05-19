package com.metacoding.springrocketdanv2.application;

public enum ApplicationStatusEnum {
    APPLIED("접수"),
    REVIEWING("검토"),
    PASSED("합격"),
    REJECTED("불합격"); // 이 코드 gpt에 넣어서 설명 작성

    public String value;

    ApplicationStatusEnum(String value) {
        this.value = value;
    }
}
