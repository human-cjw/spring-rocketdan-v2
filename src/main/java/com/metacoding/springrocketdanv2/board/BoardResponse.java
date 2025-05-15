package com.metacoding.springrocketdanv2.board;

import lombok.Data;

import java.util.List;

public class BoardResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String content;

        public DTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class ListDTO {
        private List<DTO> boards;

        public ListDTO(List<Board> boardList) {
            this.boards = boardList.stream()
                    .map(board -> new DTO(board))
                    .toList();
        }
    }

    @Data
    public static class VerifyDTO {
        private String message;

        public VerifyDTO(String message) {
            this.message = message;
        }
    }
}
