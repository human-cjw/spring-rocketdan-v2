package com.metacoding.springrocketdanv2.resume.techstack;

import lombok.Data;

public class ResumeTechStackResponse {

    @Data
    public static class UpdateDTO {
        private Integer resumeTechStackId;
        private Integer techStackId;
        private String techStackName;

        public UpdateDTO(ResumeTechStack resumeTechStack) {
            this.resumeTechStackId = resumeTechStack.getId();
            this.techStackId = resumeTechStack.getTechStack().getId();
            this.techStackName = resumeTechStack.getTechStack().getName();
        }
    }
}