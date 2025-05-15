package com.metacoding.springrocketdanv2.workfield;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(WorkFieldRepository.class)
@DataJpaTest
public class WorkFieldRepositoryTest {
    @Autowired
    private WorkFieldRepository workFieldRepository;

    @Test
    public void findAll_test() {
        // given

        // when
        List<WorkField> workFields = workFieldRepository.findAll();

        // eye
        System.out.println(workFields);
    }
}
