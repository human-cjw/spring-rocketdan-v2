package com.metacoding.springrocketdanv2.resume.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ResumeBookmarkController {
    private final ResumeBookmarkService resumeBookmarkService;
}