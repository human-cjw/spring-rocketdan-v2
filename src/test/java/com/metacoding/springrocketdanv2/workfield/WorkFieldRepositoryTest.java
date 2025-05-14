package com.metacoding.springrocketdanv2.workfield;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(WorkFieldRepository.class)
@DataJpaTest
public class WorkfieldRepositoryTest {
    @Autowired
    private WorkFieldRepository workFieldRepository;
}
