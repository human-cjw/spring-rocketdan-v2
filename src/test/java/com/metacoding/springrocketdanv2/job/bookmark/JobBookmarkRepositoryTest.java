package com.metacoding.springrocketdanv2.job.bookmark;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobBookmarkRepository.class)
@DataJpaTest
public class JobBookmarkRepositoryTest {
    @Autowired
    private JobBookmarkRepository jobBookmarkRepository;

    @Autowired
    private EntityManager em;


}
