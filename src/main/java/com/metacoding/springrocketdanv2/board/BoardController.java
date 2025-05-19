package com.metacoding.springrocketdanv2.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/api/check-board-password/{boardId}")
    public String verifyPassword(@PathVariable("boardId") Integer boardId,
                                 BoardRequest.VerifyDTO reqDTO) {
        BoardResponse.VerifyDTO respDTO = boardService.verifyPassword(boardId, reqDTO.getPassword());
        log.debug("비밀번호 검증 결과: ", respDTO);
        return null;
    }

    @GetMapping("/api/board")
    public String list() {
        BoardResponse.ListDTO respDTO = boardService.글목록보기();

        log.debug("글목록보기: " + respDTO);

        return null;
    }

    @PostMapping("/api/board")
    public String writeBoard(BoardRequest.SaveDTO reqDTO) {
        BoardResponse.DTO respDTO = boardService.글쓰기(reqDTO);

        log.debug("글쓰기: " + respDTO);

        return null;
    }

    @PutMapping("/api/board/{boardId}")
    public String update(@PathVariable("boardId") Integer boardId, BoardRequest.UpdateDTO reqDTO) { // <- form 에서 boardId와 title, content 가져와야함
        BoardResponse.DTO respDTO = boardService.글수정하기(reqDTO, boardId);

        log.debug("글수정하기: " + respDTO);

        return null;
    }

    @DeleteMapping("/api/board/{boardId}")
    public String delete(@PathVariable("boardId") Integer boardId) {
        boardService.글삭제하기(boardId);
        return null;
    }

}
