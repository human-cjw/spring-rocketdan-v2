package com.metacoding.springrocketdanv2.workfield;

import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "work_field_tb")
public class WorkField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 업무분야. it, 금융, 판매

    @Builder
    public WorkField(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkField(String name) {
        this.name = name;
    }
}
