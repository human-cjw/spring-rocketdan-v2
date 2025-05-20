package com.metacoding.springrocketdanv2.techstack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(TechStackRepository.class)
@DataJpaTest
public class TechStackRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;

    @Test
    public void findAll_test() {
        // given
        //  조건 없음 - 전체 조회

        // when
        List<TechStack> techStackList = techStackRepository.findAll();

        // eye
        if (techStackList.isEmpty()) {
            System.out.println("기술 스택 구간 데이터 없음!!!");
        } else {
            for (TechStack techStack : techStackList) {
                System.out.println("techStackId: " + techStack.getId() + ", techStackName: " + techStack.getName());
            }
        }
    }
}
