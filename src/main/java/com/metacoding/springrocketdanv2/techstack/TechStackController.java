package com.metacoding.springrocketdanv2.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TechStackController {
    private final TechStackService techStackService;
}