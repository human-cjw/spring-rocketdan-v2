package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resume.Resume;
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
@Table(name = "resume_tech_stack_tb")
public class ResumeTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 이력서 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    // 기술스택 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    @Builder
    public ResumeTechStack(Integer id, Resume resume, TechStack techStack) {
        this.id = id;
        this.resume = resume;
        this.techStack = techStack;
    }
}
