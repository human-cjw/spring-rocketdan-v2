package com.metacoding.springrocketdanv2.salaryrange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(SalaryrangeRepository.class)
@DataJpaTest
public class SalaryrangeRepositoryTest {
    @Autowired
    private SalaryrangeRepository salaryRangeRepository;
}
