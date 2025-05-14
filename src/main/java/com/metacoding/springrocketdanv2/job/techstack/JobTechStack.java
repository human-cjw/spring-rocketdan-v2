package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "job_tech_stack_tb")
public class JobTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 공고 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    // 기술스택 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    @Builder
    public JobTechStack(Integer id, Job job, TechStack techStack) {
        this.id = id;
        this.job = job;
        this.techStack = techStack;
    }
}
