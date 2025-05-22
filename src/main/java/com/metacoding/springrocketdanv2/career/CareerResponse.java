package com.metacoding.springrocketdanv2.career;

import lombok.Data;

public class CareerResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String companyName;
        private String startDate;
        private String endDate;

        public DTO(Career career) {
            this.id = career.getId();
            this.companyName = career.getCompanyName();
            this.startDate = career.getStartDate();
            this.endDate = career.getEndDate();
        }
    }
}
