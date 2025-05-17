package com.metacoding.springrocketdanv2.techstack;

import lombok.Data;

public class TechStackResponse {

    @Data
    public static class DTO {
        private Integer techStackId;
        private String name;

        public DTO(TechStack techStack) {
            this.techStackId = techStack.getId();
            this.name = techStack.getName();
        }
    }
}