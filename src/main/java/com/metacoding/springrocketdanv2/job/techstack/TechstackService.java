package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.jobTechStack.JobTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechstackService {
    private final JobTechStackRepository jobTechStackRepository;
}
