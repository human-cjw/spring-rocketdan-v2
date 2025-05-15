package com.metacoding.springrocketdanv2.jobgroup;

import lombok.Data;

public class JobGroupResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String name;

        public DTO(JobGroup jobGroup) {
            this.id = jobGroup.getId();
            this.name = jobGroup.getName();
        }
    }

    @Data
    public static class JobGroupUpdateDTO {
        private Integer id;
        private String name;
        private boolean isSelected;

        public JobGroupUpdateDTO(Integer id, String name, boolean isSelected) {
            this.id = id;
            this.name = name;
            this.isSelected = isSelected;
        }
    }
}
