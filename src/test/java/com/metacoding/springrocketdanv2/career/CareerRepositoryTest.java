package com.metacoding.springrocketdanv2.career;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(CareerRepository.class)
@DataJpaTest
public class CareerRepositoryTest {
    @Autowired
    private CareerRepository careerRepository;
}
