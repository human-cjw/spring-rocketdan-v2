package com.metacoding.springrocketdanv2.company;

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
        UserResponse.DTO sessionUserDTO = companyService.기업등록(reqDTO, sessionUser);
        session.setAttribute("sessionUser", sessionUserDTO);
        log.debug("기업 등록 완료: ", sessionUserDTO);
        return null;
    }

    @PostMapping("/company/update")
    public String update(CompanyRequest.UpdateDTO reqDTO, Errors errors, HttpSession session) {

        UserResponse.DTO sessionUser = (UserResponse.DTO) session.getAttribute("sessionUser");

        companyService.기업수정(reqDTO);

        return null;
    }

    @GetMapping("/company/job")
    public String manage(HttpSession session, Model model) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        List<CompanyResponse.CompanyManageJobDTO> respDTO = companyService.기업공고관리(sessionUser.getCompanyId());

        model.addAttribute("model", respDTO);

        return "company/manage-job";
    }

    @GetMapping("/company/job/{jobId}")
    public String manageDetail(@PathVariable Integer jobId,
                               @RequestParam(required = false) String status,
                               Model model) {
        if (status == null || status.isBlank()) {
            status = "접수";
        }

        CompanyResponse.CompanyManageResumePageDTO dto = companyService.지원자조회(jobId, status);
        model.addAttribute("model", dto);
        model.addAttribute("isStatus접수", status.equals("접수"));
        model.addAttribute("isStatus검토", status.equals("검토"));
        model.addAttribute("isStatus합격", status.equals("합격"));
        model.addAttribute("isStatus불합격", status.equals("불합격"));

        System.out.println("지원자 확인" + dto);

        return "company/manage-resume";
    }

    @GetMapping("/company/application/{applicationId}")
    public String acceptance(@PathVariable("applicationId") Integer applicationId, Model model) {
        CompanyResponse.CompanyacceptanceDTO respDTO = companyService.지원서상세보기(applicationId);
        model.addAttribute("model", respDTO);
        return "company/acceptance";
    }

    @PostMapping("/company/application/{applicationId}/accept")
    public String accept(@PathVariable("applicationId") Integer applicationId) {
        System.out.println("컨트롤러 확인용 합격" + applicationId);
        Integer jobId = companyService.지원상태수정(applicationId, "합격");
        return "redirect:/company/job/" + jobId;
    }

    @PostMapping("/company/application/{applicationId}/reject")
    public String reject(@PathVariable("applicationId") Integer applicationId) {
        System.out.println("컨트롤러 확인용 불합격" + applicationId);
        Integer jobId = companyService.지원상태수정(applicationId, "불합격");
        return "redirect:/company/job/" + jobId;
    }

    @PostMapping("/company/job/{jobId}/delete")
    public String deleteJob(@PathVariable Integer jobId, HttpSession session) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        if (sessionUser == null || !"company".equals(sessionUser.getUserType())) {
            return "redirect:/login-form";
        }

        companyService.공고삭제(jobId);

        return "redirect:/company/job";
    }
}
