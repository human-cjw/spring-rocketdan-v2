package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.jobgroup.Jobgroup;
import com.metacoding.springrocketdanv2.salaryrange.Salaryrange;
import com.metacoding.springrocketdanv2.workfield.Workfield;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "job_tb")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(columnDefinition = "text")
    private String description; // 공고 설명
    private String location; // 근무지
    private String employmentType; // 정규직, 계약직, 인턴, 프리랜서
    private String deadline; // 공고 마감일
    private String status; // 공고 상태. open, closed
    private String careerLevel; // 신입 or 경력

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    // 기업 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    // 연봉범위 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Salaryrange salaryRange;

    // 업무분야 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Workfield workField;

    // 직무 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Jobgroup jobGroup;

    // orphanRemoval = true -> 부모 엔티티와 관계가 끊어진 자식 요소는 삭제됨
    // cascade = CascadeType.ALL -> 부모 엔티티를 수정하면 자식 요소도 수정됨
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobTechStack> jobTechStacks = new ArrayList<>();

    @Builder
    public Job(Integer id, String title, String description, String location, String employmentType, String deadline, String status, String careerLevel, Timestamp createdAt, Timestamp updatedAt, Company company, Salaryrange salaryRange, Workfield workField, Jobgroup jobGroup) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.deadline = deadline;
        this.status = status;
        this.careerLevel = careerLevel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.company = company;
        this.salaryRange = salaryRange;
        this.workField = workField;
        this.jobGroup = jobGroup;
    }

    public void update(
            String title,
            String description,
            String location,
            String employmentType,
            String deadline,
            String status,
            String careerLevel,
            Salaryrange salaryRange,
            Workfield workField,
            Jobgroup jobGroup,
            List<JobTechStack> jobTechStacks
    ) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.deadline = deadline;
        this.status = status;
        this.careerLevel = careerLevel;
        this.salaryRange = salaryRange;
        this.workField = workField;
        this.jobGroup = jobGroup;
        this.jobTechStacks.clear();

        for (JobTechStack jobTechStack : jobTechStacks) {
            this.jobTechStacks.add(jobTechStack);
        }
    }
}
