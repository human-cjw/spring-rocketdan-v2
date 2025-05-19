package com.metacoding.springrocketdanv2.techstack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TechStackController {
    private final TechStackService techStackService;

    @GetMapping("/techstack")
    public String techStackList() {
        TechStackResponse.ListDTO respDTO = techStackService.목록보기();

        log.debug("기술스택목록" + respDTO);

        return null;
    }
}