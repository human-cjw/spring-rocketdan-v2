package com.metacoding.springrocketdanv2.career;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;
}
