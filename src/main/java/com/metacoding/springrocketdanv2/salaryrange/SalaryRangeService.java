package com.metacoding.springrocketdanv2.salaryrange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryRangeService {
    private final SalaryRangeRepository salaryRangeRepository;
}