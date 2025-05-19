package com.metacoding.springrocketdanv2.career;

import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import({CareerRepository.class, ResumeRepository.class})
@DataJpaTest
public class CareerRepositoryTest {
    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findAllByResumeId_test() {
        // given
        Resume resume = new Resume();
        em.persist(resume);

        Career c1 = Career.builder().companyName("A").startDate("2019-01").endDate("2020-01").resume(resume).build();
        Career c2 = Career.builder().companyName("B").startDate("2021-03").endDate("2022-03").resume(resume).build();
        em.persist(c1);
        em.persist(c2);

        em.flush();
        em.clear();

        // when
        List<Career> careers = careerRepository.findAllByResumeId(resume.getId());

        // eye
        for (Career c : careers) {
            System.out.println("career: " + c.getCompanyName() + " (" + c.getStartDate() + " ~ " + c.getEndDate() + ")");
        }
    }

    @Test
    public void save_test() {
        // given
        Resume resume = new Resume();
        em.persist(resume);

        Career career = Career.builder()
                .companyName("카카오")
                .startDate("2020-01")
                .endDate("2023-05")
                .resume(resume)
                .build();

        // when
        Career saved = careerRepository.save(career);
        em.flush();
        em.clear();

        // eye
        Career result = em.find(Career.class, saved.getId());
        System.out.println("ID: " + result.getId());
        System.out.println("회사명: " + result.getCompanyName());
        System.out.println("시작일: " + result.getStartDate());
        System.out.println("종료일: " + result.getEndDate());
        System.out.println("createdAt: " + result.getCreatedAt());
        System.out.println("resumeId: " + result.getResume().getId());
    }

    @Test
    public void deleteByResumeId_test() {
        // given
        Resume resume = new Resume();
        em.persist(resume);

        Career career = Career.builder()
                .companyName("엔씨소프트")
                .startDate("2018-01")
                .endDate("2020-12")
                .resume(resume)
                .build();
        em.persist(career);
        em.flush();
        em.clear();

        // when
        careerRepository.deleteByResumeId(resume.getId());

        em.flush();
        em.clear();

        // eye
        List<Career> careers = careerRepository.findAllByResumeId(resume.getId());
        if (careers.isEmpty()) {
            System.out.println("삭제 성공");
        } else {
            System.out.println("삭제 실패: " + careers.size() + "개");
        }
    }
}
