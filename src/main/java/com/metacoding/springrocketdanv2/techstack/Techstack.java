package com.metacoding.springrocketdanv2.techstack;

import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tech_stack_tb")
public class Techstack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 기술명, java, python...

    @Builder
    public Techstack(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
