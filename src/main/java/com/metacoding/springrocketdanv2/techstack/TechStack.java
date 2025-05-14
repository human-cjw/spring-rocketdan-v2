package com.metacoding.springrocketdanv2.techstack;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tech_stack_tb")
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 기술명, java, python...

    @Builder
    public TechStack(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
