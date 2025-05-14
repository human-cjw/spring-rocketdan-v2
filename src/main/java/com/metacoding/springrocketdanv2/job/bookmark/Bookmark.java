package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "job_bookmark_tb", uniqueConstraints = {
        @UniqueConstraint(name = "uk_job_user", // 제약조건 이름 지정
                columnNames = {"job_id", "user_id"} // 복합 유니크 제약조건을 걸 컬럼명
        )
})
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private Timestamp createdAt;

    // 유저 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 공고 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    public Bookmark(User user, Job job) {
        this.user = user;
        this.job = job;
    }
}
