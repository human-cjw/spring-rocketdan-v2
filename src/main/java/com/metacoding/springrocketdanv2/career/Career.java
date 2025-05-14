package com.metacoding.springrocketdanv2.career;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "career_tb")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName; // 이전에 다녔던 기업이름
    private String startDate; // 시작일
    private String endDate; // 종료일

    @CreationTimestamp
    private Timestamp createdAt;

    // 이력서 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @Builder
    public Career(Integer id, String companyName, String startDate, String endDate, Timestamp createdAt, JobGroup jobGroup, Resume resume) {
        this.id = id;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.resume = resume;
    }
}
