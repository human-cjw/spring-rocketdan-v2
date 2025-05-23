package com.metacoding.springrocketdanv2.company.techstack;

import lombok.Data;

public class CompanyTechStackResponse {

    @Data
    public static class DTO {
        private Integer id;
        private Integer companyId;
        private Integer techStackId;

        public DTO(CompanyTechStack companyTechStack) {
            this.id = companyTechStack.getId();
            this.companyId = companyTechStack.getCompany().getId();
            this.techStackId = companyTechStack.getTechStack().getId();
        }
    }
}
