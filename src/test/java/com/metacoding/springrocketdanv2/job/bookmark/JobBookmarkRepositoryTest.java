package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobBookmarkRepository.class)
@DataJpaTest
public class JobBookmarkRepositoryTest {
    @Autowired
    private JobBookmarkRepository jobBookmarkRepository;
}
