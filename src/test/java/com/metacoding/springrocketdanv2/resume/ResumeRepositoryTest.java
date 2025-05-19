package com.metacoding.springrocketdanv2.resume;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(ResumeRepository.class)
@DataJpaTest
public class ResumeRepositoryTest {
    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void findById_test() {
//        //given
//        Integer resumeId = 1;
//
//        // when
//        Resume resume = resumeRepository.findById(resumeId);
//
//        // eye
//        System.out.println("출력 : " + resume);
    }
}
