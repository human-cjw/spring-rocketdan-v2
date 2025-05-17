package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import lombok.Data;

public class ResumeTechStackRequest {

    @Data
    public static class UpdateDTO {
        private Integer techStackId;

        public UpdateDTO(Integer techStackId) {
            this.techStackId = techStackId;
        }

        public ResumeTechStack toEntity(Resume resume) {
            return ResumeTechStack.builder()
                    .techStack(TechStack.builder()
                            .id(techStackId)
                            .build())
                    .resume(resume)
                    .build();
        }
    }
}