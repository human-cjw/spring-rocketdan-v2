package com.metacoding.springrocketdanv2.career;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
}
