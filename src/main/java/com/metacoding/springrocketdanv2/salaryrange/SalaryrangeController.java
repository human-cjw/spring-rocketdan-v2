package com.metacoding.springrocketdanv2.salaryrange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SalaryrangeController {
    private final SalaryrangeService salaryRangeService;
}