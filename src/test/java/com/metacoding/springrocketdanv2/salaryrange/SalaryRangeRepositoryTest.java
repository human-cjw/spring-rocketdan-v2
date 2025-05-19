package com.metacoding.springrocketdanv2.salaryrange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(SalaryRangeRepository.class)
@DataJpaTest
public class SalaryRangeRepositoryTest {
    @Autowired
    private SalaryRangeRepository salaryRangeRepository;

    @Test
    public void findAll_test() {
        // given
        // (조건 없음 – 전체 조회)
        
        // when
        List<SalaryRange> salaryList = salaryRangeRepository.findAll();

        // eye
        if (salaryList.isEmpty()) {
            System.out.println("급여 구간 데이터 없음");
        } else {
            for (SalaryRange sr : salaryList) {
                System.out.println("ID: " + sr.getId()
                        + ", 최소: " + sr.getMinSalary()
                        + ", 최대: " + sr.getMaxSalary()
                        + ", 라벨: " + sr.getLabel());
            }
        }
    }
}
