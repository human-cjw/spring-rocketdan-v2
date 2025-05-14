package com.metacoding.springrocketdanv2.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class BoardRequest {
    
    @Data
    public static class SaveDTO {

        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 2, max = 50, message = "제목은 2자 이상 50자 이하로 입력해 주세요.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 5, max = 1000, message = "내용은 5자 이상 1000자 이하로 입력해 주세요.")
        private String content;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+=\\-]{4,20}$",
                message = "비밀번호는 영문과 숫자를 포함한 4자 이상 20자 이하입니다."
        )
        private String password;
    }

    @Data
    public static class UpdateDTO {

        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 2, max = 50, message = "제목은 2자 이상 50자 이하로 입력해 주세요.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 5, max = 1000, message = "내용은 5자 이상 1000자 이하로 입력해 주세요.")
        private String content;
    }
}
