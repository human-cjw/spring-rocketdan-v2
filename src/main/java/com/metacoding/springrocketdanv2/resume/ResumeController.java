package com.metacoding.springrocketdanv2.resume;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;

    @GetMapping("/api/resume/{resumeId}")
    public String detail(@PathVariable("resumeId") Integer resumeId) {
        Integer userId = null; // null or user

        ResumeResponse.DetailDTO respDTO = resumeService.이력서상세보기(resumeId, userId);
        log.debug("이력서상세보기" + respDTO);

        return null;
    }

    @PutMapping("/s/api/resume/{resumeId}")
    public String update(@PathVariable("resumeId") Integer resumeId, @Valid ResumeRequest.UpdateDTO reqDTO, Errors errors) {
        Integer sessionUserId = null;

        ResumeResponse.UpdateDTO respDTO = resumeService.이력서수정하기(resumeId, reqDTO, sessionUserId);

        log.debug("이력서수정하기" + respDTO);

        return null;
    }


    @GetMapping("/s/api/user/resume")
    public String list(@RequestParam(required = false, value = "isDefault", defaultValue = "false") boolean isDefault) {
        Integer sessionUserId = null;

        ResumeResponse.ListDTO respDTO = resumeService.이력서목록보기(sessionUserId, isDefault);

        log.debug("이력서목록보기" + respDTO);

        return null;
    }

    @DeleteMapping("/s/api/resume/{resumeId}")
    public String delete(@PathVariable("resumeId") Integer resumeId) {
        Integer sessionUserId = null;

        resumeService.이력서삭제(resumeId, sessionUserId);

        return null;
    }

    @PostMapping("/s/api/resume/")
    public String save(@Valid ResumeRequest.SaveDTO reqDTO, Errors errors) {
        Integer sessionUserId = null;

        ResumeResponse.SaveDTO respDTO = resumeService.이력서등록(reqDTO, sessionUserId);

        log.debug("이력서등록" + respDTO);

        return null;
    }
}
