package com.metacoding.springrocketdanv2.jobgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class JobGroupController {
    private final JobGroupService jobGroupService;

}
