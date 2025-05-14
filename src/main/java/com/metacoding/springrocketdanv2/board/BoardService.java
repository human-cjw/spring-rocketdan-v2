package com.metacoding.springrocketdanv2.board;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Map<String, String> verifyPassword(Integer id, String password) {
        Board board = boardRepository.findById(id);

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


    public List<BoardResponse.BoardDTO> 글목록보기() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponse.BoardDTO> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardResponse.BoardDTO dto = new BoardResponse.BoardDTO(board);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO boardDTO) {
        // BoardRequest.saveDTO 객체를 Board 엔티티 객체로 변환
        Board board = new Board(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getPassword());

        // Board 객체를 레파지토리로 저장
        boardRepository.save(board);
    }

    @Transactional
    public boolean 글수정하기(BoardRequest.UpdateDTO reqDTO, Integer boardId) {
        Board boardPS = boardRepository.findById(boardId);
        if (boardPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        boardPS.update(reqDTO.getTitle(), reqDTO.getContent());
        return true;
    }


    public Board 업데이트글보기(int id) {
        Board boardPS = boardRepository.findById(id);
        return boardPS;
    }


    @Transactional
    public void 글삭제하기(Integer id) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        boardRepository.deleteById(id);
    }
}
