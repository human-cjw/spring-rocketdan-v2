package com.metacoding.springrocketdanv2.job.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobTechStackService {
    private final JobTechStackRepository jobTechStackRepository;
}
