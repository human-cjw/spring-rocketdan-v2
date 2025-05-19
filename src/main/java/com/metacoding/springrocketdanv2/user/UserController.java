package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/s/api/user/application")
    public String userApplicationList(@RequestParam(required = false) String status) {
        Integer sessionUserId = null;

        // 유효성 검사: null 또는 허용된 값만 통과
        if (status != null && !List.of("접수", "검토", "합격", "불합격").contains(status)) {
            throw new ExceptionApi400("지원 상태는 '접수', '검토', '합격', '불합격' 중 하나여야 합니다.");
        }

        UserResponse.ListForUserDTO respDTO = userService.내지원목록보기(sessionUserId, status);

        log.debug("내지원목록보기" + respDTO);

        return null;
    }

    @GetMapping("/s/api/user/application/{applicationId}")
    public String userApplication(@PathVariable("applicationId") Integer applicationId) {
        Integer sessionUserId = null;

//        userService.내지원보기(applicationId, sessionUserId);

//        log.debug("내지원목록보기" + respDTO);

        return null;
    }
}