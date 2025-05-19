package com.metacoding.springrocketdanv2.job.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobTechStackRepository {
    private final EntityManager em;

    public void deleteByJobId(Integer jobId) {
        em.createQuery("DELETE FROM JobTechStack jts WHERE jts.job.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();
    }

    public JobTechStack save(JobTechStack jobTechStack) {
        em.persist(jobTechStack);
        return jobTechStack;
    }
}

