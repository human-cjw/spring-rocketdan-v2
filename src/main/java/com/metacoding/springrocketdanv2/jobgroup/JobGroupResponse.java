package com.metacoding.springrocketdanv2.jobgroup;

import lombok.Data;

import java.util.List;

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
    public static class ListDTO {
        List<DTO> jobGroups;

        public ListDTO(List<JobGroup> jobGroups) {
            this.jobGroups = jobGroups.stream()
                    .map(jobGroup -> new DTO(jobGroup))
                    .toList();
        }
    }
}
