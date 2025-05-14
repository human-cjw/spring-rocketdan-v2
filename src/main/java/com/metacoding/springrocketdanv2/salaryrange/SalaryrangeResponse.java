package com.metacoding.springrocketdanv2.salaryrange;

import lombok.Data;

public class SalaryrangeResponse {

    public static class SalaryRangeDTO {
        private int minSalary;
        private int maxSalary;

        public SalaryRangeDTO(int minSalary, int maxSalary) {
            this.minSalary = minSalary;
            this.maxSalary = maxSalary;
        }

        public int getMinSalary() {
            return minSalary;
        }

        public void setMinSalary(int minSalary) {
            this.minSalary = minSalary;
        }

        public int getMaxSalary() {
            return maxSalary;
        }

        public void setMaxSalary(int maxSalary) {
            this.maxSalary = maxSalary;
        }
    }

    @Data
    public static class SalaryRangeUpdateDTO {
        private Integer id;
        private String name;
        private boolean isSelected;

        public SalaryRangeUpdateDTO(Integer id, String name, boolean isSelected) {
            this.id = id;
            this.name = name;
            this.isSelected = isSelected;
        }
    }
}