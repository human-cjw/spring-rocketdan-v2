package com.metacoding.springrocketdanv2.salaryrange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SalaryRangeController {
    private final SalaryRangeService salaryRangeService;

    @GetMapping("/salaryRange")
    public String salaryRangeList() {
        SalaryRangeResponse.ListDTO respDTO = salaryRangeService.목록보기();

        log.debug("연봉범위목록" + respDTO);
        
        return null;
    }
}