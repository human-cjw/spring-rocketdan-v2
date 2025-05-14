package com.metacoding.springrocketdanv2.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/board/update/{id}")
    public @ResponseBody Map<String, String> verifyPassword(@PathVariable Integer id,
                                                            @RequestParam String password) {
        return boardService.verifyPassword(id, password);
    }

    @GetMapping("/board")
    public String list(Model model) {
        BoardResponse.ListDTO respDTO = boardService.글목록보기();
        model.addAttribute("models", respDTO);
        return "board/list2";
    }

    @PostMapping("/board/save")
    public String writeBoard(@ModelAttribute BoardRequest.SaveDTO board) {
        boardService.글쓰기(board);
        return "redirect:/board";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") Integer id, BoardRequest.UpdateDTO reqDTO) { // <- form 에서 boardId와 title, content 가져와야함
        boardService.글수정하기(reqDTO, id);

        return "redirect:/board";
    }

    @PostMapping("/board/delete")
    public String delete(@RequestParam("id") Integer id) { // boardId를 가져와서 삭제
        boardService.글삭제하기(id);
        return "redirect:/board";
    }

}
