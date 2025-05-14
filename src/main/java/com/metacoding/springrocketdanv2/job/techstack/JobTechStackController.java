package com.metacoding.springrocketdanv2.job.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class JobTechStackController {
    private final JobTechStackService jobTechStackService;
}
