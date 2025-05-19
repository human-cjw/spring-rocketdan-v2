package com.metacoding.springrocketdanv2.certification;

import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(CertificationRepository.class)
@DataJpaTest
public class CertificationRepositoryTest {
    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Test
    public void findCertificationsByResumeId_test() {

    }

    @Test
    public void deleteByResumeId_test() {

    }

    @Test
    public void save_test() {

    }

    @Test
    public void findByResumeId_test() {

    }
}
