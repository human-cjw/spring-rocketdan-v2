package com.metacoding.springrocketdanv2.jobgroup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(JobGroupRepository.class)
@DataJpaTest
public class JobGroupRepositoryTest {
    @Autowired
    private JobGroupRepository jobGroupRepository;

    @Test
    public void findAll_test() {
        // given
        // (조건 없음 – 전체 조회)

        // when
        List<JobGroup> jobGroupList = jobGroupRepository.findAll();

        // then
        if (jobGroupList.isEmpty()) {
            System.out.println("직군 데이터 없음");
        } else {
            for (JobGroup jg : jobGroupList) {
                System.out.println("직군 ID: " + jg.getId() + ", 이름: " + jg.getName());
            }
        }
    }
}
