package com.metacoding.springrocketdanv2.salaryrange;

import lombok.Data;

public class SalaryRangeResponse {

    @Data
    public static class DTO {
        private Integer salaryRangeId;
        private Integer minSalary;
        private Integer maxSalary;
        private String label;

        public DTO(SalaryRange salaryRange) {
            this.salaryRangeId = salaryRange.getId();
            this.minSalary = salaryRange.getMinSalary();
            this.maxSalary = salaryRange.getMaxSalary();
            this.label = salaryRange.getLabel();
        }
    }
}