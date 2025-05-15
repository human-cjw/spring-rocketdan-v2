package com.metacoding.springrocketdanv2.resume.techstack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(ResumeTechStackRepositoryTest.class)
@DataJpaTest
public class ResumeTechStackRepositoryTest {
    @Autowired
    private ResumeTechStackRepositoryTest resumeTechStackRepositoryTest;

    @Test
    public void findAllByResumeIdTest() {
    }
}
