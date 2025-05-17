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
}
