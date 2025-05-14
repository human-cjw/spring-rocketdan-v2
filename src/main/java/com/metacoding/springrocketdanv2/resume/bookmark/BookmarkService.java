package com.metacoding.springrocketdanv2.resume.bookmark;

import com.metacoding.springrocketdanv2.resumeBookmark.ResumeBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final ResumeBookmarkRepository resumeBookmarkRepository;
}