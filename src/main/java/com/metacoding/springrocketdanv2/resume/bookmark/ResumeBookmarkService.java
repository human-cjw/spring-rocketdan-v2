package com.metacoding.springrocketdanv2.resume.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeBookmarkService {
    private final ResumeBookmarkRepository resumeBookmarkRepository;
}