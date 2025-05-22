package com.metacoding.springrocketdanv2.techstack;

import lombok.Data;

import java.util.List;

public class TechStackResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String name;

        public DTO(TechStack techStack) {
            this.id = techStack.getId();
            this.name = techStack.getName();
        }
    }

    @Data
    public static class ListDTO {
        List<DTO> techStacks;

        public ListDTO(List<TechStack> techStacks) {
            this.techStacks = techStacks.stream()
                    .map(techStack -> new DTO(techStack))
                    .toList();
        }
    }
}