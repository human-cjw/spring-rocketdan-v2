package com.metacoding.springrocketdanv2.company.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyTechStackService {
    private final CompanyTechStackRepository companyTechStackRepository;
}
