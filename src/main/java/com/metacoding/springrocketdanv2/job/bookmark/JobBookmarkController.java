package com.metacoding.springrocketdanv2.job.bookmark;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobBookmarkController {
    private final JobBookmarkService jobBookmarkService;
    private final HttpSession session;

    @PostMapping("/s/api/job-bookmark")
    public String save(@Valid JobBookmarkRequest.SaveDTO reqDTO, Errors errors) {
        Integer sessionUserId = null;
        JobBookmarkResponse.SaveDTO respDTO = jobBookmarkService.북마크등록(reqDTO, sessionUserId);
        log.debug("북마크등록" + respDTO);
        return null;
    }

    @GetMapping("/s/api/job-bookmark")
    public String list() {
        Integer sessionUserId = null;
        JobBookmarkResponse.ListDTO respDTO = jobBookmarkService.북마크목록보기(sessionUserId);
        log.debug("북마크목록보기" + respDTO);
        return null;
    }

    @DeleteMapping("/s/api/job-bookmark/{jobBookmarkId}")
    public String delete(@PathVariable("jobBookmarkId") Integer jobBookmarkId) {
        Integer sessionUserId = null;
        jobBookmarkService.북마크삭제(jobBookmarkId, sessionUserId);
        return null;
    }
}
