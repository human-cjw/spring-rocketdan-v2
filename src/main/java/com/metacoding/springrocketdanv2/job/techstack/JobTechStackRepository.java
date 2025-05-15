package com.metacoding.springrocketdanv2.job.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobTechStackRepository {
    private final EntityManager em;

    public void deleteJobTechStacksByJobId(Integer jobId) {
        String q = "DELETE FROM JobTechStack jts WHERE jts.job.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
    }
}

