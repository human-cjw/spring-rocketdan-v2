package com.metacoding.springrocketdanv2.resume.bookmark;

import com.metacoding.springrocketdanv2.resumeBookmark.ResumeBookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(ResumeBookmarkRepository.class)
@DataJpaTest
public class BookmarkRepositoryTest {
    @Autowired
    private ResumeBookmarkRepository resumeBookmarkRepository;
}
