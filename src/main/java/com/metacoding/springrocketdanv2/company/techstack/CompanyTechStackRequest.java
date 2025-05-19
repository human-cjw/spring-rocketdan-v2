package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import lombok.Data;

public class CompanyTechStackRequest {

    @Data
    public static class UpdateDTO {
        private Integer techStackId;

        public UpdateDTO(Integer techStackId) {
            this.techStackId = techStackId;
        }

        public CompanyTechStack toEntity(Company company) {
            return CompanyTechStack.builder()
                    .techStack(TechStack.builder()
                            .id(techStackId)
                            .build())
                    .company(company)
                    .build();
        }
    }
}
