package com.metacoding.springrocketdanv2.certification;

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
@Table(name = "certification_tb")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 자격증이름
    private String issuer; // 자격증발급기관
    private String issuedDate; // 자격증발급일자

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @Builder
    public Certification(Integer id, String name, String issuer, String issuedDate, Timestamp createdAt, Resume resume) {
        this.id = id;
        this.name = name;
        this.issuer = issuer;
        this.issuedDate = issuedDate;
        this.createdAt = createdAt;
        this.resume = resume;
    }
}
