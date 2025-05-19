package com.metacoding.springrocketdanv2.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findAll_test() {
        // given
        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        if (boardList.isEmpty()) {
            System.out.println("boardList is empty!");
        } else {
            for (Board board : boardList) {
                System.out.println("id: " + board.getId() + ", title: " + board.getTitle());
            }
        }
    }

    @Test
    public void save_test() {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .password("1234")
                .build();

        // when
        boardRepository.save(board);
        em.flush();
        em.clear();

        // eye
        Board result = boardRepository.findById(board.getId()).orElseThrow();
        System.out.println("ID: " + result.getId());
        System.out.println("Title: " + result.getTitle());
        System.out.println("Content: " + result.getContent());
        System.out.println("Password: " + result.getPassword());
        System.out.println("CreatedAt: " + result.getCreatedAt());
    }

    @Test
    public void findById_test() {
        // given
        Integer boardId = 1;

        // when
        Board board = boardRepository.findById(boardId).orElse(null);

        // eye
        if (board == null) {
            System.out.println("board is null!");
        } else {
            System.out.println("title: " + board.getTitle() + ", content: " + board.getContent());
        }
    }

    @Test
    public void deleteById_test() {
        // given
        Board saved = boardRepository.save(Board.builder()
                .title("제목")
                .content("내용")
                .password("1234")
                .build());
        Integer boardId = saved.getId();

        // when
        boardRepository.deleteById(boardId);

        // eye
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board == null) {
            System.out.println("삭제 성공: board is null");
        } else {
            System.out.println("삭제 실패: board still exists → title = " + board.getTitle());
        }
    }
}
