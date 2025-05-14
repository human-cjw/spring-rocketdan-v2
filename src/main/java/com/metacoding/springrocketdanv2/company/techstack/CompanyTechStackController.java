package com.metacoding.springrocketdanv2.company.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CompanyTechStackController {
    private final CompanyTechStackService companyTechStackService;
}
