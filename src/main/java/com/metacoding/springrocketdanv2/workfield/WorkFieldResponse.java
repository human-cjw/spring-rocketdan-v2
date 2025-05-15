package com.metacoding.springrocketdanv2.workfield;

import lombok.Data;

import java.util.List;

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

    @Data
    public static class ListDTO {
        private List<DTO> workFields;

        public ListDTO(List<WorkField> workFields) {
            this.workFields = workFields.stream()
                    .map((workField) -> new DTO(workField))
                    .toList();
        }
    }
}