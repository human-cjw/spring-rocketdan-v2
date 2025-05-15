package com.metacoding.springrocketdanv2.board;

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
        Board board = new Board("제목", "내용", "1234");

        // when
        boardRepository.save(board);

        // then
        System.out.println("board 저장 완료 title = " + board.getTitle());
    }

    @Test
    public void findById_test() {
        // given
        Integer boardId = 1;

        // when
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다"));

        // then
        if (board == null) {
            System.out.println("board is null!");
        } else {
            System.out.println("title: " + board.getTitle() + ", content: " + board.getContent());
        }
    }

    @Test
    public void deleteById_test() {
        // given
        Integer boardId = 1;

        // when
        boardRepository.deleteById(boardId);

        // then
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board == null) {
            System.out.println("삭제 성공: board is null");
        } else {
            System.out.println("삭제 실패: board still exists → title = " + board.getTitle());
        }
    }
}
