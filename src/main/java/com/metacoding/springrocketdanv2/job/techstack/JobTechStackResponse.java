package com.metacoding.springrocketdanv2.job.techstack;

import lombok.Data;

public class JobTechStackResponse {

    @Data
    public static class DTO {
        private Integer id;
        private Integer jobId;
        private Integer techStackId;

        public DTO(JobTechStack jobTechStack) {
            this.id = jobTechStack.getId();
            this.jobId = jobTechStack.getJob().getId();
            this.techStackId = jobTechStack.getTechStack().getId();
        }
    }
}
