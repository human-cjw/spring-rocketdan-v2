package com.metacoding.springrocketdanv2.career;

import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(CareerRepository.class)
@DataJpaTest
public class CareerRepositoryTest {
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void findCareersByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        List<Career> careerList = careerRepository.findCareersByResumeId(resumeId);

        // then
        if (careerList.isEmpty()) {
            System.out.println("해당 resumeId로 조회된 경력 없음");
        } else {
            for (Career career : careerList) {
                System.out.println("회사명: " + career.getCompanyName() + ", 시작일: " + career.getStartDate());
            }
        }
    }

    @Test
    public void deleteByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        careerRepository.deleteByResumeId(resumeId);

        // then
        List<Career> result = careerRepository.findCareersByResumeId(resumeId);
        if (result.isEmpty()) {
            System.out.println("삭제 성공: 해당 이력서에 연결된 경력 없음");
        } else {
            System.out.println("삭제 실패: 아직 경력 남아 있음 → " + result.size() + "개");
        }
    }

    @Test
    public void save_test() {
        // given
        Resume resume = resumeRepository.findById(1);
        Career career = new Career(null, "삼성전자", "2021-01", "2023-12", null, null, resume);

        // when
        careerRepository.save(career);

        // then
        System.out.println("career 저장 완료 companyName = " + career.getCompanyName());
    }

    @Test
    public void findByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        Career career = careerRepository.findByResumeId(resumeId);

        // then
        if (career == null) {
            System.out.println("해당 resumeId에 대한 경력 정보 없음");
        } else {
            System.out.println("조회된 경력 → 회사명: " + career.getCompanyName() + ", 시작일: " + career.getStartDate());
        }
    }
}
