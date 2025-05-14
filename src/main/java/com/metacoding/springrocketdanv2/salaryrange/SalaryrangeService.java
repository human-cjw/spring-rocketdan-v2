package com.metacoding.springrocketdanv2.salaryrange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryrangeService {
    private final SalaryrangeRepository salaryRangeRepository;
}