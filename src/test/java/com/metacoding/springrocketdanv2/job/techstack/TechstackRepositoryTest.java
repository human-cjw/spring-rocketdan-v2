package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.jobTechStack.JobTechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobTechStackRepository.class)
@DataJpaTest
public class TechstackRepositoryTest {
    @Autowired
    private JobTechStackRepository jobTechStackRepository;
}
