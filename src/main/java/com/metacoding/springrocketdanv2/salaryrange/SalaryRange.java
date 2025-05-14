package com.metacoding.springrocketdanv2.salaryrange;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "salary_range_tb")
public class SalaryRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer minSalary;
    private Integer maxSalary;
    private String label; // 구간표시 "minSalary-maxSalary"

    @Builder
    public SalaryRange(Integer id, Integer minSalary, Integer maxSalary, String label) {
        this.id = id;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.label = label;
    }
}
