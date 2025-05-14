package com.metacoding.springrocketdanv2.techstack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TechStackRepository.class)
@DataJpaTest
public class TechStackRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;
}
