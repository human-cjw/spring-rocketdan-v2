package com.metacoding.springrocketdanv2.workfield;

import lombok.Data;

public class WorkFieldResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String name;

        public DTO(WorkField workField) {
            this.id = workField.getId();
            this.name = workField.getName();
        }
    }
}