package com.metacoding.springrocketdanv2.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        String sql = "SELECT * FROM board_tb order by id desc";  // 네이티브 SQL 쿼리
        Query query = em.createNativeQuery(sql, Board.class);
        return query.getResultList();
    }

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public Optional<Board> findByBoardId(Integer id) {
        Board boardPS = em.find(Board.class, id);
        return Optional.ofNullable(boardPS);
    }

    public void deleteByBoardId(Integer id) {
        em.createQuery("delete from Board b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
