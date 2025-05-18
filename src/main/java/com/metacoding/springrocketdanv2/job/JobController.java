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

    @GetMapping({"/", "/api/job"})
    public String list() {
        JobResponse.ListDTO respDTO = jobService.글목록보기(null);
        log.debug("공고목록보기" + respDTO);
        return null;
    }

    @GetMapping("/api/job/{jobId}")
    public String detail(@PathVariable("jobId") Integer jobId) {
        JobResponse.DetailDTO respDTO = jobService.글상세보기(jobId, null);

        log.debug("공고상세보기" + respDTO);

        return null;
    }

    @PostMapping("/s/api/job")
    public String save(@Valid JobRequest.SaveDTO reqDTO, Errors errors) {
        Integer companyId = null;

        JobResponse.SaveDTO respDTO = jobService.등록하기(reqDTO, companyId);

        log.debug("공고등록" + respDTO);

        return null;
    }

    @PutMapping("/s/api/job/{jobId}")
    public String update(@PathVariable("jobId") Integer jobId,
                         @Valid JobRequest.UpdateDTO reqDTO, Errors errors) {
        Integer sessionUserCompanyId = null;

        JobResponse.UpdateDTO respDTO = jobService.수정하기(jobId, reqDTO, sessionUserCompanyId);

        log.debug("공고수정" + respDTO);

        return null;
    }

    @DeleteMapping("/s/api/job/{jobId}")
    public String delete(@PathVariable("jobId") Integer jobId) {
        Integer companyId = null;

        jobService.삭제하기(jobId, companyId);

        return null;
    }
}