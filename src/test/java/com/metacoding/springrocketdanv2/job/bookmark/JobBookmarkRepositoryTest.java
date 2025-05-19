package com.metacoding.springrocketdanv2.job.bookmark;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobBookmarkRepository.class)
@DataJpaTest
public class JobBookmarkRepositoryTest {
    @Autowired
    private JobBookmarkRepository jobBookmarkRepository;

    private EntityManager em;

    @Test
    public void save_test() {
        // given
        Integer bookmarkId = 1;

        // when
        JobBookmark jobBookmark = em.find(JobBookmark.class, bookmarkId);

        // then
        System.out.println("ID: " + jobBookmark.getId());
        System.out.println("User ID: " + jobBookmark.getUser().getId());
        System.out.println("Job ID: " + jobBookmark.getJob().getId());
        System.out.println("생성일: " + jobBookmark.getCreatedAt());
    }
}
