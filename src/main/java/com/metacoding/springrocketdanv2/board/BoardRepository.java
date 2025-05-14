package com.metacoding.springrocketdanv2.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        String sql = "SELECT * FROM board_tb order by id desc";  // 네이티브 SQL 쿼리
        Query query = em.createNativeQuery(sql, Board.class);
        return query.getResultList();
    }

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Integer id) {
        return em.find(Board.class, id);
    }

    public void deleteById(Integer id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }
}
