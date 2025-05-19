package com.metacoding.springrocketdanv2.company;


import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.util.Resp;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final HttpSession session;

    @GetMapping("/api/company/{companyId}")
    public ResponseEntity<?> detail(@PathVariable Integer companyId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Integer sessionUserCompanyId = sessionUser != null ? sessionUser.getCompanyId() : null;

        CompanyResponse.DetailDTO respDTO = companyService.기업상세(companyId, sessionUserCompanyId);

        log.debug("기업상세: ", respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/api/company")
    public ResponseEntity<?> list() {
        CompanyResponse.ListDTO respDTO = companyService.기업리스트();

        log.debug("회사 목록: ", respDTO);

        return Resp.ok(respDTO);
    }

    @PostMapping("/s/api/company")
    public ResponseEntity<?> save(CompanyRequest.SaveDTO reqDTO) {
        Integer sessionUserId = null;

        CompanyResponse.SaveDTO respDTO = companyService.기업등록(reqDTO, sessionUserId);

        log.debug("기업등록" + respDTO);

        return Resp.ok(respDTO);
    }

    @PutMapping("/s/api/company/{companyId}")
    public ResponseEntity<?> update(@RequestParam("companyId") Integer companyId, CompanyRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        CompanyResponse.UpdateDTO respDTO = companyService.기업수정(reqDTO, companyId, sessionUser.getCompanyId());

        log.debug("기업수정", respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/company/job")
    public ResponseEntity<?> manage() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        CompanyResponse.JobListDTO respDTO = companyService.기업공고관리(sessionUser.getCompanyId());

        log.debug("기업 공고 관리 목록: ", respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/company/job/{jobId}/application")
    public ResponseEntity<?> companyApplicationList(@PathVariable Integer jobId,
                                                    @RequestParam(value = "status", defaultValue = "접수") String status) {

        // 유효성 검사: null 또는 허용된 값만 통과
        if (status != null && !List.of("접수", "검토", "합격", "불합격").contains(status)) {
            throw new ExceptionApi400("지원 상태는 '접수', '검토', '합격', '불합격' 중 하나여야 합니다.");
        }

        CompanyResponse.ApplicationListDTO respDTO = companyService.지원자조회(jobId, status);

        log.debug("지원자 확인: ", respDTO);

        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/company/application/{applicationId}")
    public ResponseEntity<?> companyApplication(@PathVariable("applicationId") Integer applicationId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        CompanyResponse.ApplicationDetailDTO respDTO = companyService.지원서상세보기(applicationId, sessionUser.getCompanyId());

        log.debug("지원서상세보기-기업" + respDTO);

        return Resp.ok(respDTO);
    }
}

