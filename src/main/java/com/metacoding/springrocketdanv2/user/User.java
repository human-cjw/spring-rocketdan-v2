package com.metacoding.springrocketdanv2.user;

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
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String fileUrl; // 프로필 이미지
    @Column(nullable = false)
    private String userType; // 일반유저, 기업유저 값 : user or company

    // 기업 fk
    private Integer companyId; // userType이 user 면 null

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public User(Integer id, String username, String password, String email, String fileUrl, String userType, Integer companyId, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fileUrl = fileUrl;
        this.userType = userType;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }
}
