package com.metacoding.springrocketdanv2.resume.bookmark;

import com.metacoding.springrocketdanv2.resumeBookmark.ResumeBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookmarkController {
    private final ResumeBookmarkService resumeBookmarkService;
}