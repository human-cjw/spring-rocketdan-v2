package com.metacoding.springrocketdanv2.resume.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ResumeTechStackController {
    private final ResumeTechStackService resumeTechStackService;
}