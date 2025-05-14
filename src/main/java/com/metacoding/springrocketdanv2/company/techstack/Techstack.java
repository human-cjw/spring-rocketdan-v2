package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "company_tech_stack_tb")
public class Techstack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 기업 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    // 기술스택 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private com.metacoding.springrocketdanv2.techstack.Techstack techStack;

    public Techstack(Company company, com.metacoding.springrocketdanv2.techstack.Techstack techStack) {
        this.company = company;
        this.techStack = techStack;
    }
}


