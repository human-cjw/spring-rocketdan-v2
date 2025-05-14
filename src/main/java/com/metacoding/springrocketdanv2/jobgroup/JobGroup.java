package com.metacoding.springrocketdanv2.jobgroup;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "job_group_tb")
public class JobGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 직군. 백엔드 개발자, 프론트엔드 개발자

    @Builder
    public JobGroup(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
