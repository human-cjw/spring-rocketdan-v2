package com.metacoding.springrocketdanv2.techstack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TechStackRepository.class)
@DataJpaTest
public class TechStackRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;

    @Test
    public void findAll_test() {

    }

    @Test
    public void findById_test() {

    }

    @Test
    public void findByName_test() {

    }
}
