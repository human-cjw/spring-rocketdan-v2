package com.metacoding.springrocketdanv2.salaryrange;

import com.metacoding.springrocketdanv2._core.util.Resp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SalaryRangeController {
    private final SalaryRangeService salaryRangeService;

    @GetMapping("/salaryrange")
    public ResponseEntity<?> salaryRangeList() {
        SalaryRangeResponse.ListDTO respDTO = salaryRangeService.목록보기();

        log.debug("연봉범위목록" + respDTO);

        return Resp.ok(respDTO);
    }
}