package com.metacoding.springrocketdanv2.reply;

import com.metacoding.springrocketdanv2.board.Board;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reply_tb")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    // 유저 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 게시물 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
