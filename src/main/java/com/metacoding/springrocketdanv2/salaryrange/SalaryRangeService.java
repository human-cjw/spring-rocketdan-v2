package com.metacoding.springrocketdanv2.salaryrange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryRangeService {
    private final SalaryRangeRepository salaryRangeRepository;

    public SalaryRangeResponse.ListDTO 목록보기() {
        List<SalaryRange> salaryRangesPS = salaryRangeRepository.findAll();

        return new SalaryRangeResponse.ListDTO(salaryRangesPS);
    }
}