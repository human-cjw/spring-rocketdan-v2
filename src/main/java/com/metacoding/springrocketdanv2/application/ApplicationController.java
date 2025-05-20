package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.util.Resp;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    @PostMapping("/s/api/application")
    public ResponseEntity<?> save(@Valid ApplicationRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ApplicationResponse.SaveDTO respDTO = applicationService.지원하기(reqDTO, sessionUser.getId());

        log.debug("지원하기" + respDTO);

        return Resp.ok(respDTO);
    }

    @PutMapping("/s/api/application/{applicationId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("applicationId") Integer applicationId, ApplicationRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ApplicationResponse.UpdateDTO respDTO = applicationService.지원상태변경(applicationId, reqDTO, sessionUser.getCompanyId());

        log.debug("지원상태변경" + respDTO);

        return Resp.ok(respDTO);
    }
}
