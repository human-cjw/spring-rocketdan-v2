package com.metacoding.springrocketdanv2.jobgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class JobGroupController {
    private final JobGroupService jobGroupService;

    @GetMapping
    public String apiJobGroup() {

    }
}
