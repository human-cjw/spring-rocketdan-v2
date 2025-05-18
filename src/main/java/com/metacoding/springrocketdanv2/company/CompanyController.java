package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.application.ApplicationResponse;
import com.metacoding.springrocketdanv2.application.ApplicationService;
import com.metacoding.springrocketdanv2.job.JobService;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkFieldRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final ApplicationService applicationService;
    private final JobService jobService;
    private final WorkFieldRepository workFieldRepository;
    private final TechStackRepository techStackRepository;

    @GetMapping("/api/company/{companyId}")
    public String detail(@PathVariable Integer companyId, HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        CompanyResponse.DetailDTO respDTO = companyService.기업상세(companyId, sessionUser);
        log.debug("기업상세: ", respDTO);
        return null;
    }

    @GetMapping("/api/company")
    public String list(HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        CompanyResponse.ListDTO respDTO = companyService.기업리스트(sessionUser);
        log.debug("회사 목록: ", respDTO);
        return null;
    }

    @PostMapping("/s/api/company")
    public String save(CompanyRequest.SaveDTO reqDTO, HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        CompanyRequest.SaveDTO sessionUserDTO = companyService.기업등록(reqDTO, sessionUser.getId());
        session.setAttribute("sessionUser", sessionUserDTO);
        log.debug("기업 등록 완료: ", sessionUserDTO);
        return null;
    }

    @PostMapping("/s/api/company/update")
    public String update(CompanyRequest.UpdateDTO reqDTO, Errors errors, HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        companyService.기업수정(reqDTO);
        log.debug("기업 수정 완료: ", reqDTO);
        return null;
    }

    @GetMapping("/s/api/company/job")
    public String manage(HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        CompanyResponse.JobListDTO respDTO = companyService.기업공고관리(sessionUser.getCompanyId());
        log.debug("기업 공고 관리 목록: ", respDTO);
        return null;
    }

    @GetMapping("/s/api/company/job/{jobId}")
    public String manageDetail(@PathVariable Integer jobId,
                               @RequestParam(defaultValue = "접수") String status,
                               HttpSession session) {
        CompanyResponse.ResumeListDTO respDTO = companyService.지원자조회(jobId, status);
        log.debug("지원자 확인: ", respDTO);
        return null;
    }

    @GetMapping("/s/api/company/application/{applicationId}")
    public String acceptance(@PathVariable Integer applicationId) {
        ApplicationResponse.AcceptanceDTO respDTO = applicationService.지원서상세보기(applicationId);
        log.debug("지원서 상세: ", respDTO);
        return null;
    }

    @PostMapping("/s/api/job/{jobId}/delete")
    public String deleteJob(@PathVariable Integer jobId, HttpSession session) {
        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");
        jobService.공고삭제(jobId);
        return null;
    }
}
