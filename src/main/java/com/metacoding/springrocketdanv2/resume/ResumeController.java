package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2._core.util.Resp;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;

    @GetMapping("/api/resume/{resumeId}")
    public ResponseEntity<?> detail(@PathVariable("resumeId") Integer resumeId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();

        ResumeResponse.DetailDTO respDTO = resumeService.이력서상세보기(resumeId, sessionUserId);
        log.debug("이력서상세보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @PutMapping("/s/api/resume/{resumeId}")
    public ResponseEntity<?> update(@PathVariable("resumeId") Integer resumeId, @Valid ResumeRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ResumeResponse.UpdateDTO respDTO = resumeService.이력서수정하기(resumeId, reqDTO, sessionUser.getId());

        log.debug("이력서수정하기" + respDTO);

        return Resp.ok(respDTO);
    }


    @GetMapping("/s/api/user/resume")
    public ResponseEntity<?> list(@RequestParam(required = false, value = "isDefault", defaultValue = "false") boolean isDefault) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ResumeResponse.ListDTO respDTO = resumeService.이력서목록보기(sessionUser.getId(), isDefault);

        log.debug("이력서목록보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/s/api/resume/{resumeId}")
    public ResponseEntity<?> delete(@PathVariable("resumeId") Integer resumeId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        resumeService.이력서삭제(resumeId, sessionUser.getId());

        return Resp.ok(null);
    }

    @PostMapping("/s/api/resume/")
    public ResponseEntity<?> save(@Valid ResumeRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ResumeResponse.SaveDTO respDTO = resumeService.이력서등록(reqDTO, sessionUser.getId());

        log.debug("이력서등록" + respDTO);

        return Resp.ok(respDTO);
    }
}
