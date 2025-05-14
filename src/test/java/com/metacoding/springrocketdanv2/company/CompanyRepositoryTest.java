package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.application.Application;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional

@Import(CompanyRepository.class)


public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void findById_test() {
        Integer id = 1;
        Company company = companyRepository.findById(id);
        if (company == null) {
            System.out.println("company is null!");
        } else {
            System.out.println("company name: " + company.getNameKr());
        }
    }

    @Test
    void resumeIsNotNull만조회되는지확인() {
        // given
        Integer jobId = 1; // 테스트할 공고 ID

        // when
        List<Application> applications = companyRepository.findApplicationsByJobIdWhereResumeNotNull(jobId);

        // then
        for (Application app : applications) {
            assertNotNull(app.getResume(), "resume은 null이 아니어야 함");
            System.out.println("지원 ID: " + app.getId() + ", 이력서 ID: " + app.getResume().getId());
        }

        // 개수 출력
        System.out.println("총 조회된 지원 수: " + applications.size());
    }
}

