package com.metacoding.springrocketdanv2.jobgroup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JobGroupController {
    private final JobGroupService jobGroupService;

    @GetMapping("/jobGroup")
    public String jobGroupList() {
        JobGroupResponse.ListDTO respDTO = jobGroupService.목록보기();

        log.debug("업무목록" + respDTO);

        return null;
    }
}
