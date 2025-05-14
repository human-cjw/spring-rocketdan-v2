package com.metacoding.springrocketdanv2.certification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(CertificationRepository.class)
@DataJpaTest
public class CertificationRepositoryTest {
    @Autowired
    private CertificationRepository certificationRepository;
}
