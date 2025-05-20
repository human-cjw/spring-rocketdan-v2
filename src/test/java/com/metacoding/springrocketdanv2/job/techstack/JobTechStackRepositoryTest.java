package com.metacoding.springrocketdanv2.job.techstack;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(JobTechStackRepository.class)
@DataJpaTest
public class JobTechStackRepositoryTest {
    @Autowired
    private JobTechStackRepository jobTechStackRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void deleteByJobId_test() {
        // given
        Integer jobId = 1;

        // when
        jobTechStackRepository.deleteByJobId(jobId);
        em.flush();
        em.clear();

        // eye
        List<JobTechStack> result = em.createQuery("SELECT jts FROM JobTechStack jts WHERE jts.job.id = :jobId", JobTechStack.class)
                .setParameter("jobId", jobId)
                .getResultList();

        if (result.isEmpty()) {
            System.out.println("삭제 성공: JobTechStack이 없습니다.");
        } else {
            System.out.println("삭제 실패: JobTechStack이 아직 존재합니다.");
        }
    }

    @Test
    public void save_test() {
        // given
        Job job = em.find(Job.class, 1);
        TechStack techStack = em.find(TechStack.class, 1);

        JobTechStack jobTechStack = JobTechStack.builder()
                .job(job)
                .techStack(techStack)
                .build();

        // when
        JobTechStack saved = jobTechStackRepository.save(jobTechStack);
        em.flush();
        em.clear();

        // eye
        JobTechStack result = em.find(JobTechStack.class, saved.getId());
        System.out.println("id: " + result.getId());
        System.out.println("jobId: " + result.getJob().getId());
        System.out.println("techStackId: " + result.getTechStack().getId());
    }
}
