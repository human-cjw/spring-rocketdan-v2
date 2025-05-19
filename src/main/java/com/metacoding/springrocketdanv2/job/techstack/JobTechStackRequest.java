package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import lombok.Data;

public class JobTechStackRequest {

    @Data
    public static class UpdateDTO {
        private Integer techStackId;

        public UpdateDTO(Integer techStackId) {
            this.techStackId = techStackId;
        }

        public JobTechStack toEntity(Job job) {
            return JobTechStack.builder()
                    .techStack(TechStack.builder()
                            .id(techStackId)
                            .build())
                    .job(job)
                    .build();
        }
    }
}
