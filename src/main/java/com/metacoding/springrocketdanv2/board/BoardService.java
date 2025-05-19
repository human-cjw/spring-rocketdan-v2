package com.metacoding.springrocketdanv2.board;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponse.VerifyDTO verifyPassword(Integer boardId, String password) {
        Board boardPS = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 글입니다"));

        if (boardPS.getPassword().equals(password)) {
            return new BoardResponse.VerifyDTO(true, "비밀번호가 맞습니다.");
        } else {
            return new BoardResponse.VerifyDTO(false, "비밀번호가 틀렸습니다.");
        }
    }

    public BoardResponse.ListDTO 글목록보기() {
        List<Board> boardsPS = boardRepository.findAll();
        BoardResponse.ListDTO respDTO = new BoardResponse.ListDTO(boardsPS);
        return respDTO;
    }

    @Transactional
    public BoardResponse.DTO 글쓰기(BoardRequest.SaveDTO reqDTO) {
        // BoardRequest.saveDTO 객체를 Board 엔티티 객체로 변환
        Board board = reqDTO.toEntity();

        // Board 객체를 레파지토리로 저장
        Board BoardPS = boardRepository.save(board);
        return new BoardResponse.DTO(BoardPS);
    }

    @Transactional
    public BoardResponse.DTO 글수정하기(BoardRequest.UpdateDTO reqDTO, Integer boardId) {
        Board boardPS = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        boardPS.update(reqDTO.getTitle(), reqDTO.getContent());
        return new BoardResponse.DTO(boardPS);
    }

    @Transactional
    public void 글삭제하기(Integer boardId) {
        boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 글입니다"));

        boardRepository.deleteByBoardId(boardId);
    }
}
