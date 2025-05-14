package com.metacoding.springrocketdanv2.resume.bookmark;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "resume_bookmark_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_resume_company", // 제약조건 이름 지정
                columnNames = {"resume_id", "company_id"} // 복합 유니크 제약조건을 걸 컬럼명
        )
})
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private Timestamp createdAt;

    // 이력서 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    // 기업 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
}
