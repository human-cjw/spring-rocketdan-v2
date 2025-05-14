package com.metacoding.springrocketdanv2.board;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Map<String, String> verifyPassword(Integer boardId, String password) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 글입니다"));

        Map<String, String> response = new HashMap<>();
        if (board.getPassword().equals(password)) {
            response.put("status", "success");
            response.put("message", "비밀번호가 맞습니다.");
        } else {
            response.put("status", "error");
            response.put("message", "비밀번호가 틀렸습니다.");
        }
        return response;
    }


    public BoardResponse.ListDTO 글목록보기() {
        List<Board> boardsPS = boardRepository.findAll();
        BoardResponse.ListDTO respDTO = new BoardResponse.ListDTO(boardsPS);
        return respDTO;
    }

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO boardDTO) {
        // BoardRequest.saveDTO 객체를 Board 엔티티 객체로 변환
        Board board = new Board(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getPassword());

        // Board 객체를 레파지토리로 저장
        boardRepository.save(board);
    }

    @Transactional
    public BoardResponse.DTO 글수정하기(BoardRequest.UpdateDTO reqDTO, Integer boardId) {
        Board boardOP = boardRepository.findById(boardId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        boardOP.update(reqDTO.getTitle(), reqDTO.getContent());
        return new BoardResponse.DTO(boardOP);
    }

    @Transactional
    public void 글삭제하기(Integer boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 글입니다"));

        boardRepository.deleteById(boardId);
    }
}
