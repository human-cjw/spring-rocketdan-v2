package com.metacoding.springrocketdanv2.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TechstackController {
    private final TechstackService techStackService;
}