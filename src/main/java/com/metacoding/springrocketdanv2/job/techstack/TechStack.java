package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.job.Job;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "job_tech_stack_tb")
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 공고 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    // 기술스택 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private com.metacoding.springrocketdanv2.techstack.TechStack techStack;

    @Builder
    public TechStack(Integer id, Job job, com.metacoding.springrocketdanv2.techstack.TechStack techStack) {
        this.id = id;
        this.job = job;
        this.techStack = techStack;
    }
}
