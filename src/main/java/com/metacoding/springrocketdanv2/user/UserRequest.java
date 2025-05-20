package com.metacoding.springrocketdanv2.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {
        @NotBlank(message = "아이디는 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 영문자와 숫자 조합으로 4자 이상 20자 이하입니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(
                regexp = "^[A-Za-z\\d!@#$%^&*()_+=\\-]{4,20}$",
                message = "비밀번호는 4자 이상 20자 이하로 숫자, 영문, 특수문자를 포함할 수 있습니다."
        )
        private String password;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력해 주세요.")
        private String email;
        
        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .userType(UserTypeEnum.USER.value)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        @NotBlank(message = "아이디는 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 영문자와 숫자 조합으로 4자 이상 20자 이하입니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하입니다.")
        private String password;
    }
}