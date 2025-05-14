package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    @GetMapping("/user/job/{jobId}/apply-form")
    public String applyForm(@PathVariable("jobId") Integer jobId,
                            HttpServletRequest request) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        if (sessionUserDTO.getCompanyId() != null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        ApplicationResponse.ApplyDTO respDTO = applicationService.지원보기(jobId, sessionUserDTO);

        request.setAttribute("model", respDTO);

        return "user/application/apply-form";
    }

    @PostMapping("/user/job/{jobId}/apply/save")
    public String applySave(@PathVariable("jobId") Integer jobId,
                            @Valid ApplicationRequest.SaveDTO reqDTO, Errors errors) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        applicationService.지원하기(jobId, reqDTO, sessionUserDTO.getId());
        return "redirect:/user/job/" + jobId + "/apply-done";
    }

    @GetMapping("/user/job/{jobId}/apply-done")
    public String applyDone(@PathVariable("jobId") Integer jobId,
                            HttpServletRequest request) {
        ApplicationResponse.ApplyDoneDTO respDTO = applicationService.지원완료(jobId);

        request.setAttribute("model", respDTO);

        return "user/application/apply-done";
    }

    @GetMapping("/user/application")
    public String userApplication(@RequestParam(required = false) String status, HttpServletRequest request) {
        // 유저 지원 관리 페이지 데이터 가져와서 DTO로 던져주기
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        List<ApplicationResponse.ApplicationManageDTO> applicationManageDTOS = applicationService.내지원목록보기(sessionUserDTO.getId(), status);
        request.setAttribute("applicationManageDTOS", applicationManageDTOS);

        request.setAttribute("isAll", status == null);
        request.setAttribute("isApplied", "접수".equals(status));
        request.setAttribute("isReviewing", "검토".equals(status));
        request.setAttribute("isPassed", "합격".equals(status));
        request.setAttribute("isRejected", "불합격".equals(status));

        return "user/application/list";
    }

    @GetMapping("/user/application/process/job/{jobId}")
    public String userApplicationProcess(HttpServletRequest request, @PathVariable("jobId") Integer jobId) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        ApplicationResponse.ProcessDTO2 respDTO = applicationService.입사지원현황보기(sessionUserDTO.getId(), jobId);
        request.setAttribute("model", respDTO);

        return "user/application/process";
    }
}
