package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.techstack.TechstackRepository;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkfieldRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final WorkfieldRepository workFieldRepository;
    private final TechstackRepository techStackRepository;


    @GetMapping("/company/{companyId}")
    public String detail(@PathVariable("companyId") Integer companyId, Model model, HttpSession session) {
        CompanyResponse.CompanyResponseDTO responseDTO = companyService.기업상세(companyId);

        // 현재 로그인한 유저 정보 가져오기
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        boolean isOwner = false;
        if (sessionUser != null) {
            if ("company".equals(sessionUser.getUserType()) && sessionUser.getCompanyId() != null) {
                if (sessionUser.getCompanyId().equals(companyId)) {
                    isOwner = true;
                }
            }
        }

        responseDTO.setOwner(isOwner);

        model.addAttribute("model", responseDTO);
        return "company/detail";
    }

    @GetMapping("/company")
    public String list(HttpServletRequest request, HttpSession session, Model model) {
        List<Company> companyList = companyService.기업리스트();
        request.setAttribute("models", companyList);

        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        if (sessionUser != null) {
            model.addAttribute("isCompany", "company".equals(sessionUser.getUserType()));
        } else {
            model.addAttribute("isCompany", false);
        }

        return "company/list";
    }

    @GetMapping("/company/save-form")
    public String saveForm(Model model) {

        model.addAttribute("model", companyService.등록보기());
        return "company/save-form";
    }

    @PostMapping("/company/save")
    public String save(@Valid @ModelAttribute CompanyRequest.CompanySaveDTO requestDTO, Errors errors, HttpSession session) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        UserResponse.SessionUserDTO sessionUserDTO = companyService.기업등록(requestDTO, sessionUser);
        session.setAttribute("sessionUser", sessionUserDTO);
        return "redirect:/company/" + sessionUserDTO.getCompanyId();
    }

    @GetMapping("/company/update-form")
    public String updateForm(HttpSession session, Model model) {
        // 1. 세션에서 로그인한 사용자 꺼내기
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다."); // 세션 없으면 막기
        }

        // 2. 세션에 유저 저장
        session.setAttribute("sessionUser", sessionUser);

        // 3. 유저 ID를 이용해서 내 기업 정보 조회
        CompanyResponse.UpdateFormDTO dto = companyService.내기업조회(sessionUser.getId());

        model.addAttribute("model", dto);
        return "company/update-form";
    }


    @PostMapping("/company/update")
    public String update(@Valid @ModelAttribute CompanyRequest.UpdateDTO requestDTO, Errors errors, HttpSession session) {

        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        companyService.기업수정(requestDTO);

        return "redirect:/company/" + requestDTO.getId();
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
