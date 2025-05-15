package com.metacoding.springrocketdanv2.job.techstack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobTechStackRepository.class)
@DataJpaTest
public class JobTechStackRepositoryTest {
    @Autowired
    private JobTechStackRepository jobTechStackRepository;
}
