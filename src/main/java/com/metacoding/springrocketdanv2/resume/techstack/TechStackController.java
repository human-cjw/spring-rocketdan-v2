package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TechStackController {
    private final ResumeTechStackService resumeTechStackService;
}