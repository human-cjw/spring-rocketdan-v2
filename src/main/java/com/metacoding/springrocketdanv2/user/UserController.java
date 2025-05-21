package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.util.Resp;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        UserResponse.TokenDTO respDTO = userService.로그인(reqDTO);
        log.debug("로그인한 유저" + respDTO);
        return Resp.ok(respDTO);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        log.debug("회원가입한 유저" + respDTO);
        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/user/application")
    public ResponseEntity<?> userApplicationList(@RequestParam(required = false) String status) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 유효성 검사: null 또는 허용된 값만 통과
        if (status != null && !List.of("접수", "검토", "합격", "불합격").contains(status)) {
            throw new ExceptionApi400("지원 상태는 '접수', '검토', '합격', '불합격' 중 하나여야 합니다.");
        }

        UserResponse.ListForUserDTO respDTO = userService.내지원목록보기(sessionUser.getId(), status);

        log.debug("내지원목록보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/user/application/{applicationId}")
    public ResponseEntity<?> userApplication(@PathVariable("applicationId") Integer applicationId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        UserResponse.ApplicationDetailDTO respDTO = userService.내지원보기(applicationId, sessionUser.getId());

        log.debug("내지원보기" + respDTO);

        return Resp.ok(respDTO);
    }
}