package com.metacoding.springrocketdanv2.job;

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
public class JobController {
    private final JobService jobService;
    private final HttpSession session;

    @GetMapping({"/", "/api/job"})
    public ResponseEntity<?> list() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Integer sessionUserId = sessionUser != null ? sessionUser.getId() : null;

        JobResponse.ListDTO respDTO = jobService.글목록보기(sessionUserId);

        log.debug("공고목록보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/api/job/{jobId}")
    public ResponseEntity<?> detail(@PathVariable("jobId") Integer jobId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // service에서 sessionUser null 체크함
        JobResponse.DetailDTO respDTO = jobService.글상세보기(jobId, sessionUser);

        log.debug("공고상세보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @PostMapping("/s/api/job")
    public ResponseEntity<?> save(@Valid @RequestBody JobRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        JobResponse.SaveDTO respDTO = jobService.등록하기(reqDTO, sessionUser.getCompanyId());

        log.debug("공고등록" + respDTO);

        return Resp.ok(respDTO);
    }

    @PutMapping("/s/api/job/{jobId}")
    public ResponseEntity<?> update(@PathVariable("jobId") Integer jobId,
                                    @Valid @RequestBody JobRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        JobResponse.UpdateDTO respDTO = jobService.수정하기(jobId, reqDTO, sessionUser.getCompanyId());

        log.debug("공고수정" + respDTO);

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/s/api/job/{jobId}")
    public ResponseEntity<?> delete(@PathVariable("jobId") Integer jobId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        jobService.삭제하기(jobId, sessionUser.getCompanyId());

        return Resp.ok(null);
    }
}