package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.user.User;
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
@Table(name = "application_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_company", // 제약조건 이름 지정
                columnNames = {"user_id", "company_id"} // 복합 유니크 제약조건을 걸 컬럼명
        )
})
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status; // 지원 상태 -> 접수, 검토, 합격, 불합격

    @CreationTimestamp
    private Timestamp createdAt;

    // 이력서 FK
    @ManyToOne(fetch = FetchType.LAZY, optional = true) // optional = true -> jpa 에서 null 가능
    private Resume resume; // resume -> id , resume -> title , description resume.getTitle()

    // 공고 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;    // 이력서 FK

    // 이력서 주인 FK
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private User user;

    // 공고 주인 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Builder
    public Application(Integer id, String status, Timestamp createdAt, Resume resume, Job job, User user, Company company) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.resume = resume;
        this.job = job;
        this.user = user;
        this.company = company;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}
