package com.metacoding.springrocketdanv2.job;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final HttpSession session;

    @GetMapping("/")
    public String list() {
        JobResponse.ListDTO respDTO = jobService.글목록보기(null);
        log.debug("공고목록보기" + respDTO);
        return null;
    }

    @GetMapping("/job/{jobId}")
    public String show(@PathVariable("jobId") Integer jobId) {
        JobResponse.DetailDTO respDTO = jobService.글상세보기(jobId, null);

        log.debug("공고상세보기" + respDTO);

        return null;
    }

    @PostMapping("/job")
    public String save(@Valid JobRequest.SaveDTO reqDTO, Errors errors) {
//        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        JobResponse.SaveDTO respDTO = jobService.등록하기(reqDTO, null);

        log.debug("공고등록" + respDTO);

        return null;
    }


    @PutMapping("/job/{jobId}")
    public String update(@PathVariable("jobId") Integer jobId,
                         @Valid JobRequest.UpdateDTO reqDTO, Errors errors) {
        JobResponse.UpdateDTO respDTO = jobService.수정하기(jobId, reqDTO);

        log.debug("공고수정" + respDTO);

        return null;
    }
}