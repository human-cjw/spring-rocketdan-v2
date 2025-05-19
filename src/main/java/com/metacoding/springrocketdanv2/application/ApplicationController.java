package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    @PostMapping("/s/api/application")
    public String save(@Valid ApplicationRequest.SaveDTO reqDTO, Errors errors) {
        Integer sessionUserId = null;

        applicationService.지원하기(reqDTO, sessionUserId);

        return null;
    }

    @GetMapping("/s/api/user/application")
    public String userApplicationList(@RequestParam(required = false) String status) {
        Integer sessionUserId = null;

        // 유효성 검사: null 또는 허용된 값만 통과
        if (status != null && !List.of("접수", "검토", "합격", "불합격").contains(status)) {
            throw new ExceptionApi400("지원 상태는 '접수', '검토', '합격', '불합격' 중 하나여야 합니다.");
        }

        ApplicationResponse.ListForUserDTO respDTO = applicationService.내지원목록보기(sessionUserId, status);

        log.debug("내지원목록보기" + respDTO);

        return null;
    }

    @PutMapping("/s/api/application/{applicationId}/status")
    public String updateStatus(@PathVariable("applicationId") Integer applicationId, ApplicationRequest.UpdateDTO reqDTO) {
        Integer sessionUserCompanyId = null;
        applicationService.지원상태변경(applicationId, reqDTO, sessionUserCompanyId);

        return null;
    }
}
