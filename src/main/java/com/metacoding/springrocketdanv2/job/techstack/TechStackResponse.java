package com.metacoding.springrocketdanv2.job.techstack;

import lombok.Data;

public class TechStackResponse {

    @Data
    public static class JobTechStackUpdateDTO {
        private Integer id;
        private String name;
        private boolean isChecked;

        public JobTechStackUpdateDTO(
                Integer id,
                String name,
                boolean isChecked
        ) {
            this.id = id;
            this.name = name;
            this.isChecked = isChecked;
        }
    }
}
