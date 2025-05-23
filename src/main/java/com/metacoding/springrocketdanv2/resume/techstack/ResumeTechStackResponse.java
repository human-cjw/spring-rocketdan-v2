package com.metacoding.springrocketdanv2.resume.techstack;

import lombok.Data;

public class ResumeTechStackResponse {

    @Data
    public static class UpdateDTO {
        private Integer id;
        private Integer techStackId;
        private String techStackName;

        public UpdateDTO(ResumeTechStack resumeTechStack) {
            this.id = resumeTechStack.getId();
            this.techStackId = resumeTechStack.getTechStack().getId();
            this.techStackName = resumeTechStack.getTechStack().getName();
        }
    }

    @Data
    public static class DTO {
        private Integer id;
        private Integer techStackId;
        private Integer resumeId;

        public DTO(ResumeTechStack resumeTechStack) {
            this.id = resumeTechStack.getId();
            this.techStackId = resumeTechStack.getTechStack().getId();
            this.resumeId = resumeTechStack.getResume().getId();
        }
    }
}