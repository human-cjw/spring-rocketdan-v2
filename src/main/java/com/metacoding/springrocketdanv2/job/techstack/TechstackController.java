package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.jobTechStack.JobTechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TechstackController {
    private final JobTechStackService jobTechStackService;
}
