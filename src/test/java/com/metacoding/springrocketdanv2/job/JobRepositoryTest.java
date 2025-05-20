package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStack;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@Import(JobRepository.class)
@DataJpaTest
public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findAll_test() {
        // given

        // when
        List<Job> jobs = jobRepository.findAll();
        List<JobResponse.DTO> dtos = jobs.stream()
                .map(job -> new JobResponse.DTO(job))
                .toList();

        // eye
        for (JobResponse.DTO dto : dtos) {
            System.out.println("ID: " + dto.getJobId());
            System.out.println("제목: " + dto.getTitle());
            System.out.println("설명: " + dto.getDescription());
            System.out.println("위치: " + dto.getLocation());
            System.out.println("고용형태: " + dto.getEmploymentType());
            System.out.println("마감일: " + dto.getDeadline());
            System.out.println("상태: " + dto.getStatus());
            System.out.println("경력레벨: " + dto.getCareerLevel());
            System.out.println("생성일: " + dto.getCreatedAt());
            System.out.println("수정일: " + dto.getUpdatedAt());
            System.out.println("회사ID: " + dto.getCompanyId());
            System.out.println("회사명: " + dto.getCompanyName());
            System.out.println("연봉: " + dto.getSalaryRange().getLabel());
            System.out.println("업무: " + dto.getWorkField().getName());
            System.out.println("직무: " + dto.getJobGroup().getName());
            System.out.println();
        }
    }

    @Test
    public void findByJobId_test() {
        // given
        Integer jobId = 1;

        // when
        Optional<Job> jobOP = jobRepository.findByJobId(jobId);

        // eye
        if (jobOP.isPresent()) {
            Job job = jobOP.get();
            System.out.println("ID: " + job.getId());
            System.out.println("제목: " + job.getTitle());
        } else {
            System.out.println("해당 ID의 Job을 찾을 수 없습니다.");
        }
    }

    @Test
    public void save_test() {
        // given
        Company company = Company.builder()
                .nameKr("메타코딩")
                .phone("010-1234-5678")
                .contactManager("김매니저")
                .build();
        em.persist(company);

        SalaryRange salaryRangeRef = em.getReference(SalaryRange.class, 1);
        WorkField workFieldRef = em.getReference(WorkField.class, 1);
        JobGroup jobGroupRef = em.getReference(JobGroup.class, 1);

        Job job = Job.builder()
                .title("백엔드 개발자")
                .description("스프링 부트 기반 백엔드 개발")
                .location("서울")
                .employmentType("정규직")
                .deadline("2025-06-30")
                .status("공개")
                .careerLevel("3년 이상")
                .company(company)
                .salaryRange(salaryRangeRef)
                .workField(workFieldRef)
                .jobGroup(jobGroupRef)
                .build();

        // when
        Job saved = jobRepository.save(job);
        em.flush();
        em.clear();

        // eye
        Job result = em.find(Job.class, saved.getId());
        System.out.println("ID: " + result.getId());
        System.out.println("제목: " + result.getTitle());
        System.out.println("연봉" + result.getSalaryRange().getLabel());
        System.out.println("업무" + result.getWorkField().getName());
        System.out.println("직무" + result.getJobGroup().getName());
    }

    @Test
    public void findByJobIdJoinFetchAll_test() {
        // given
        Integer jobId = 1;

        // when
        Optional<Job> jobOP = jobRepository.findByJobIdJoinFetchAll(jobId);

        // eye
        if (jobOP.isPresent()) {
            Job job = jobOP.get();
            System.out.println("ID: " + job.getId());
            System.out.println("제목: " + job.getTitle());
            System.out.println("회사명: " + job.getCompany().getNameKr());
            System.out.println("연봉범위: " + job.getSalaryRange().getLabel());
            System.out.println("업무: " + job.getWorkField().getName());
            System.out.println("직무: " + job.getJobGroup().getName());

            System.out.println("기술스택:");
            for (JobTechStack jts : job.getJobTechStacks()) {
                System.out.println("- " + jts.getTechStack().getName());
            }
        } else {
            System.out.println("해당 ID의 Job을 찾을 수 없습니다.");
        }
    }

    @Test
    public void deleteByJobId_test() {
        // given
        Integer jobId = 1;

        // 연관 테이블부터 삭제
        em.createQuery("DELETE FROM Application a WHERE a.job.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();

        em.createQuery("DELETE FROM JobBookmark b WHERE b.job.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();

        em.createQuery("DELETE FROM JobTechStack jts WHERE jts.job.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();

        // 부모 삭제
        jobRepository.deleteByJobId(jobId);
        em.flush();
        em.clear();

        // eye
        Job job = em.find(Job.class, jobId);
        if (job == null) {
            System.out.println("삭제 성공: Job이 존재하지 않습니다.");
        } else {
            System.out.println("삭제 실패: Job이 아직 존재합니다.");
        }
    }

    @Test
    public void updateByJobId_test() {
        // given
        Integer jobId = 1;

        JobRequest.UpdateDTO reqDTO = new JobRequest.UpdateDTO();
        reqDTO.setTitle("수정된 제목");
        reqDTO.setDescription("수정된 설명");
        reqDTO.setLocation("서울");
        reqDTO.setEmploymentType("정규직");
        reqDTO.setDeadline("2025-12-31");
        reqDTO.setStatus("open");
        reqDTO.setCareerLevel("경력");
        reqDTO.setSalaryRangeId(1);
        reqDTO.setWorkFieldId(1);
        reqDTO.setJobGroupId(1);

        // when
        jobRepository.updateByJobId(jobId, reqDTO);
        em.flush();
        em.clear();

        // eye
        Job job = em.find(Job.class, jobId);
        System.out.println("제목: " + job.getTitle());
        System.out.println("설명: " + job.getDescription());
        System.out.println("위치: " + job.getLocation());
        System.out.println("고용형태: " + job.getEmploymentType());
        System.out.println("마감일: " + job.getDeadline());
        System.out.println("상태: " + job.getStatus());
        System.out.println("경력: " + job.getCareerLevel());
        System.out.println("연봉 라벨: " + job.getSalaryRange().getLabel());
        System.out.println("업무: " + job.getWorkField().getName());
        System.out.println("직무: " + job.getJobGroup().getName());
    }

    @Test
    public void findAllByCompanyId_test() {
        // given
        Integer companyId = 1;

        // when
        List<Job> jobs = jobRepository.findAllByCompanyId(companyId);

        // eye
        for (Job job : jobs) {
            System.out.println("=================================");
            System.out.println("Job ID: " + job.getId());
            System.out.println("제목: " + job.getTitle());
            System.out.println("기업명: " + job.getCompany().getNameKr());
            System.out.println("마감일: " + job.getDeadline());
            System.out.println("상태: " + job.getStatus());
            System.out.println("총 개수: " + jobs.size());
            System.out.println("=================================");
        }
    }
}