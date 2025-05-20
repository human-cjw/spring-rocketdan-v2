package com.metacoding.springrocketdanv2.job.bookmark;

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
public class JobBookmarkController {
    private final JobBookmarkService jobBookmarkService;
    private final HttpSession session;

    @PostMapping("/s/api/job-bookmark")
    public ResponseEntity<?> save(@Valid @RequestBody JobBookmarkRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        JobBookmarkResponse.SaveDTO respDTO = jobBookmarkService.북마크등록(reqDTO, sessionUser.getId());

        log.debug("북마크등록" + respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/job-bookmark")
    public ResponseEntity<?> list() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        JobBookmarkResponse.ListDTO respDTO = jobBookmarkService.북마크목록보기(sessionUser.getId());

        log.debug("북마크목록보기" + respDTO);

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/s/api/job-bookmark/{jobBookmarkId}")
    public ResponseEntity<?> delete(@PathVariable("jobBookmarkId") Integer jobBookmarkId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        jobBookmarkService.북마크삭제(jobBookmarkId, sessionUser.getId());

        return Resp.ok(null);
    }
}
