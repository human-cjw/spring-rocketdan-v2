package com.metacoding.springrocketdanv2.resume;

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


@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final HttpSession session;

    @GetMapping("/resume/{resumeId}")
    public String detail(@PathVariable("resumeId") Integer resumeId, HttpServletRequest request) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) request.getSession().getAttribute("sessionUser");
        Integer userId = sessionUser.getId(); // 세션에서 유저 ID 꺼내기

        ResumeResponse.DetailDTO detailDTO = resumeService.이력서상세보기(resumeId, userId);

        request.setAttribute("model", detailDTO);
        return "resume/detail";
    }


    @GetMapping("/resume/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer resumeId, HttpServletRequest request) {

        ResumeResponse.UpdateDTO respDTO = resumeService.이력서수정보기(resumeId);

        request.setAttribute("model", respDTO);

        return "resume/update-form";
    }


    @PostMapping("/resume/{resumeId}/update")
    public String update(@PathVariable("resumeId") Integer resumeId, @Valid ResumeRequest.UpdateDTO requestDTO, Errors errors) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        resumeService.이력서수정하기(resumeId, requestDTO, sessionUserDTO.getId());

        return "redirect:/resume/" + resumeId;
    }


    @GetMapping("/user/resume")
    public String list(HttpServletRequest request,
                       @RequestParam(required = false, value = "default", defaultValue = "") String isDefault) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        ResumeResponse.ResumeListDTO respDTO = resumeService.이력서목록보기(sessionUserDTO.getId(), Boolean.parseBoolean(isDefault));

        request.setAttribute("model", respDTO);

        return "resume/list";
    }


    @GetMapping("/resume/save-form")
    public String saveForm(HttpServletRequest request) {
        ResumeResponse.SaveDTO respDTO = resumeService.이력서등록보기();
        request.setAttribute("model", respDTO);
        return "resume/save-form";
    }

    @GetMapping("/user/resume/{resumeId}/delete")
    public String delete(@PathVariable("resumeId") Integer resumeId) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        resumeService.이력서삭제(resumeId, sessionUserDTO.getId());
        return "redirect:/user/resume";
    }

    @PostMapping("/resume/save")
    public String save(@Valid ResumeRequest.SaveDTO reqDTO, Errors errors) {
        UserResponse.SessionUserDTO sessionUserDTO = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
        resumeService.이력서등록(sessionUserDTO.getId(), reqDTO);
        return "redirect:/user/resume";
    }
}
