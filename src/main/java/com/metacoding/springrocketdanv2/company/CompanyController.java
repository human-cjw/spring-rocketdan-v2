package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String detail(@PathVariable Integer companyId) {
        Integer sessionUserCompanyId = null;

        CompanyResponse.DetailDTO respDTO = companyService.기업상세(companyId, sessionUserCompanyId);

        log.debug("기업상세: ", respDTO);

        return null;
    }

    @GetMapping("/api/company")
    public String list() {
        CompanyResponse.ListDTO respDTO = companyService.기업리스트();

        log.debug("회사 목록: ", respDTO);

        return null;
    }

    @PostMapping("/s/api/company")
    public String save(CompanyRequest.SaveDTO reqDTO) {
        Integer sessionUserId = null;

        CompanyResponse.SaveDTO respDTO = companyService.기업등록(reqDTO, sessionUserId);

        log.debug("기업등록" + respDTO);

        return null;
    }

    @PutMapping("/s/api/company/{companyId}")
    public String update(@RequestParam("companyId") Integer companyId, CompanyRequest.UpdateDTO reqDTO, Errors errors) {
        Integer sessionUserCompanyId = null;

        CompanyResponse.UpdateDTO respDTO = companyService.기업수정(reqDTO, companyId, sessionUserCompanyId);

        log.debug("기업수정", respDTO);

        return null;
    }

    @GetMapping("/s/api/company/job")
    public String manage() {
        Integer sessionUserCompanyId = null;

        CompanyResponse.JobListDTO respDTO = companyService.기업공고관리(sessionUserCompanyId);

        log.debug("기업 공고 관리 목록: ", respDTO);

        return null;
    }

    @GetMapping("/s/api/company/job/{jobId}/application")
    public String companyApplicationList(@PathVariable Integer jobId,
                                         @RequestParam(value = "status", defaultValue = "접수") String status) {

        // 유효성 검사: null 또는 허용된 값만 통과
        if (status != null && !List.of("접수", "검토", "합격", "불합격").contains(status)) {
            throw new ExceptionApi400("지원 상태는 '접수', '검토', '합격', '불합격' 중 하나여야 합니다.");
        }

        CompanyResponse.ApplicationListDTO respDTO = companyService.지원자조회(jobId, status);

        log.debug("지원자 확인: ", respDTO);

        return null;
    }

    @GetMapping("/s/api/company/application/{applicationId}")
    public String companyApplication(@PathVariable("applicationId") Integer applicationId) {
        Integer sessionUserCompanyId = null;
        CompanyResponse.ApplicationDetailDTO respDTO = companyService.지원서상세보기(applicationId, sessionUserCompanyId);

        log.debug("지원서상세보기-기업" + respDTO);

        return null;
    }
}

