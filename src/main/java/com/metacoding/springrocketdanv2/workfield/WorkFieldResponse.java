package com.metacoding.springrocketdanv2.workfield;

import lombok.Data;

public class WorkFieldResponse {

    @Data
    public static class WorkFieldUpdateDTO {
        private Integer id;
        private String name;
        private boolean isSelected;

        public WorkFieldUpdateDTO(Integer id, String name, boolean isSelected) {
            this.id = id;
            this.name = name;
            this.isSelected = isSelected;
        }
    }
}