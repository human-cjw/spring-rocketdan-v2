package com.metacoding.springrocketdanv2.board;

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
@Table(name = "board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String password;

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Board(Integer id, String title, String content, String password, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.password = password;
        this.createdAt = createdAt;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



/*
    // 유저 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
*/
}
