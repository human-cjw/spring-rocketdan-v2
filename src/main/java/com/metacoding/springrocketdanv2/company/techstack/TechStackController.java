package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.companyTechStack.CompanyTechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TechStackController {
    private final CompanyTechStackService companyTechStackService;
}
