package com.metacoding.springrocketdanv2.salaryrange;

import lombok.Data;

import java.util.List;

public class SalaryRangeResponse {

    @Data
    public static class DTO {
        private Integer id;
        private Integer minSalary;
        private Integer maxSalary;
        private String label;

        public DTO(SalaryRange salaryRange) {
            this.id = salaryRange.getId();
            this.minSalary = salaryRange.getMinSalary();
            this.maxSalary = salaryRange.getMaxSalary();
            this.label = salaryRange.getLabel();
        }
    }

    @Data
    public static class ListDTO {
        private List<DTO> salaryRanges;

        public ListDTO(List<SalaryRange> salaryRanges) {
            this.salaryRanges = salaryRanges.stream()
                    .map(salaryRange -> new DTO(salaryRange))
                    .toList();
        }
    }
}