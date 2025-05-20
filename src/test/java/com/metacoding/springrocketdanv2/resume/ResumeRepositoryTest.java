package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStack;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@Import(ResumeRepository.class)
@DataJpaTest
public class ResumeRepositoryTest {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        Optional<Resume> resumeOP = resumeRepository.findByResumeId(resumeId);

        // eye
        if (resumeOP.isPresent()) {
            Resume resume = resumeOP.get();
            System.out.println("ID: " + resume.getId());
            System.out.println("제목: " + resume.getTitle());
        } else {
            System.out.println("해당 이력서를 찾을 수 없습니다.");
        }
    }

    @Test
    public void findByResumeIdJoinFetchAll_test() {
        // given
        Integer resumeId = 1;

        // when
        Optional<Resume> resumeOP = resumeRepository.findByResumeIdJoinFetchAll(resumeId);

        // eye
        if (resumeOP.isPresent()) {
            Resume resume = resumeOP.get();
            System.out.println("이력서 ID: " + resume.getId());
            System.out.println("제목: " + resume.getTitle());
            System.out.println("연봉: " + resume.getSalaryRange().getLabel());
            System.out.println("직무: " + resume.getJobGroup().getName());
            System.out.println("유저명: " + resume.getUser().getUsername());

            System.out.println("기술스택 목록:");
            for (ResumeTechStack rts : resume.getResumeTechStacks()) {
                System.out.println("- " + rts.getTechStack().getName());
            }
        } else {
            System.out.println("해당 ID의 이력서를 찾을 수 없습니다.");
        }
    }

    @Test
    public void findAllByUserId_test() {
        // given
        Integer userId = 1;

        // when
        List<Resume> defaultResumes = resumeRepository.findAllByUserId(userId, true);
        List<Resume> allResumes = resumeRepository.findAllByUserId(userId, false);

        // eye
        System.out.println("==== [isDefault = true] ====");
        for (Resume resume : defaultResumes) {
            System.out.println("ID: " + resume.getId() + ", 제목: " + resume.getTitle() + ", isDefault: " + resume.getIsDefault());
        }

        System.out.println("==== [isDefault = false] ====");
        for (Resume resume : allResumes) {
            System.out.println("ID: " + resume.getId() + ", 제목: " + resume.getTitle() + ", isDefault: " + resume.getIsDefault());
        }
    }

    @Test
    public void findByUserIdAndIsDefaultTrue_test() {
        // given
        Integer userId = 1;

        // when
        Optional<Resume> resumeOP = resumeRepository.findByUserIdAndIsDefaultTrue(userId);

        // eye
        if (resumeOP.isPresent()) {
            Resume resume = resumeOP.get();
            System.out.println("ID: " + resume.getId());
            System.out.println("제목: " + resume.getTitle());
            System.out.println("isDefault: " + resume.getIsDefault());
        } else {
            System.out.println("기본 이력서가 없습니다.");
        }
    }

    @Test
    public void deleteByResumeId_test() {
        // given
        Integer resumeId = 1;

        // 1. 연관 테이블들 먼저 삭제
        em.createQuery("DELETE FROM Application a WHERE a.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();

        em.createQuery("DELETE FROM Career c WHERE c.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();

        em.createQuery("DELETE FROM Certification ct WHERE ct.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();

        em.createQuery("DELETE FROM ResumeTechStack rts WHERE rts.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();

        em.createQuery("DELETE FROM ResumeBookmark rb WHERE rb.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();

        // 2. 부모 테이블(Resume) 삭제
        resumeRepository.deleteByResumeId(resumeId);
        em.flush();
        em.clear();

        // 3. eye
        Resume result = em.find(Resume.class, resumeId);
        if (result == null) {
            System.out.println("삭제 성공: Resume가 존재하지 않습니다.");
        } else {
            System.out.println("삭제 실패: Resume가 아직 존재합니다.");
        }
    }

    @Test
    public void save_test() {
        // given
        User user = User.builder().username("ssar").password("1234").email("ssar@nate.com").userType("user").build();
        em.persist(user);

        SalaryRange salaryRange = SalaryRange.builder().label("4000만원 이상").minSalary(4000).maxSalary(6000).build();
        em.persist(salaryRange);

        JobGroup jobGroup = JobGroup.builder().name("백엔드").build();
        em.persist(jobGroup);

        Resume resume = Resume.builder()
                .title("백엔드 개발자 이력서")
                .summary("3년 경력, 스프링 숙련")
                .careerLevel("경력")
                .isDefault(true)
                .user(user)
                .salaryRange(salaryRange)
                .jobGroup(jobGroup)
                .build();

        // when
        Resume saved = resumeRepository.save(resume);
        em.flush();
        em.clear();

        // eye
        Resume result = em.find(Resume.class, saved.getId());
        System.out.println("ID: " + result.getId());
        System.out.println("제목: " + result.getTitle());
        System.out.println("요약: " + result.getSummary());
        System.out.println("경력레벨: " + result.getCareerLevel());
        System.out.println("isDefault: " + result.getIsDefault());
        System.out.println("userId: " + result.getUser().getId());
        System.out.println("salaryRange: " + result.getSalaryRange().getLabel());
        System.out.println("jobGroup: " + result.getJobGroup().getName());
    }

    @Test
    public void updateByResumeId_test() {
        // given
        Integer resumeId = 1;

        ResumeRequest.UpdateDTO reqDTO = new ResumeRequest.UpdateDTO();
        reqDTO.setTitle("수정된 제목");
        reqDTO.setSummary("수정된 요약");
        reqDTO.setPortfolioUrl("https://new-portfolio.com");
        reqDTO.setIsDefault(true);
        reqDTO.setSalaryRangeId(1);
        reqDTO.setJobGroupId(1);

        // when
        resumeRepository.updateByResumeId(resumeId, reqDTO);
        em.flush();
        em.clear();

        // eye
        Resume result = em.find(Resume.class, resumeId);
        System.out.println("제목: " + result.getTitle());
        System.out.println("요약: " + result.getSummary());
        System.out.println("포트폴리오: " + result.getPortfolioUrl());
        System.out.println("기본이력서 여부: " + result.getIsDefault());
        System.out.println("연봉: " + result.getSalaryRange().getLabel());
        System.out.println("직무: " + result.getJobGroup().getName());
    }
}
