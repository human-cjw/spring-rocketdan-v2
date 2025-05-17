package com.metacoding.springrocketdanv2.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO reqDTO, Errors errors) {
        UserResponse.TokenDTO respDTO = userService.로그인(reqDTO);
        log.debug("로그인한 유저" + respDTO);
        return null;
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        log.debug("회원가입한 유저" + respDTO);
        return null;
    }
}