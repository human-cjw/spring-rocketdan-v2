package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.companyTechStack.CompanyTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final CompanyTechStackRepository companyTechStackRepository;
}
