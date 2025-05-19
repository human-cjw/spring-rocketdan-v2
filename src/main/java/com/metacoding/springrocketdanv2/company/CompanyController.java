package com.metacoding.springrocketdanv2.company;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    public String manageDetail(@PathVariable Integer jobId,
                               @RequestParam(value = "status", defaultValue = "접수") String status) {
        CompanyResponse.ApplicationListDTO respDTO = companyService.지원자조회(jobId, status);

        log.debug("지원자 확인: ", respDTO);

        return null;
    }

    //--------------------------------------여기까지 완료-----------------------------------------------------
//    @GetMapping("/s/api/company/application/{applicationId}")
//    public String acceptance(@PathVariable("applicationId") Integer applicationId) {
//        companyService.지원서상세보기(applicationId);
//        return null;
//    }
//
//    @PostMapping("/company/application/{applicationId}/accept")
//    public String accept(@PathVariable("applicationId") Integer applicationId) {
//        System.out.println("컨트롤러 확인용 합격" + applicationId);
//        Integer jobId = companyService.지원상태수정(applicationId, "합격");
//        return "redirect:/company/job/" + jobId;
//    }
//
//    @PostMapping("/company/application/{applicationId}/reject")
//    public String reject(@PathVariable("applicationId") Integer applicationId) {
//        System.out.println("컨트롤러 확인용 불합격" + applicationId);
//        Integer jobId = companyService.지원상태수정(applicationId, "불합격");
//        return "redirect:/company/job/" + jobId;
//    }
//
//    @PostMapping("/company/job/{jobId}/delete")
//    public String deleteJob(@PathVariable Integer jobId, HttpSession session) {
//        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
//        if (sessionUser == null || !"company".equals(sessionUser.getUserType())) {
//            return "redirect:/login-form";
//        }
//
//        companyService.공고삭제(jobId);
//
//        return "redirect:/company/job";
//    }
}
