package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ResumeTechStackRepositoryTest.class)
@DataJpaTest
public class ResumeTechStackRepositoryTest {
    @Autowired
    private ResumeTechStackRepositoryTest resumeTechStackRepositoryTest;

    @Test
    public void findAllByResumeIdTest() {
        // given
        Integer resumeId = 1;

        // when
        List<ResumeTechStack> resumeTechStacks = resumeTechStackRepositoryTest.findAllByResumeId(resumeId);

        // eye

        for (ResumeTechStack resumeTechStack : resumeTechStacks) {
            System.out.println("결과==================" + resumeTechStacks);
        }
    }
}
