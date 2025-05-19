package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.util.Resp;
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
        Integer sessionUserId = null;

        ApplicationResponse.SaveDTO respDTO = applicationService.지원하기(reqDTO, sessionUserId);

        log.debug("지원하기" + respDTO);

        return Resp.ok(respDTO);
    }

    @PutMapping("/s/api/application/{applicationId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("applicationId") Integer applicationId, ApplicationRequest.UpdateDTO reqDTO) {
        Integer sessionUserCompanyId = null;

        ApplicationResponse.UpdateDTO respDTO = applicationService.지원상태변경(applicationId, reqDTO, sessionUserCompanyId);

        log.debug("지원상태변경" + respDTO);

        return Resp.ok(respDTO);
    }
}
