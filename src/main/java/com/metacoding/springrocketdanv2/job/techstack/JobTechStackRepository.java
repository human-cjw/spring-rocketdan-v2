package com.metacoding.springrocketdanv2.job.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobTechStackRepository {
    private final EntityManager em;

<<<<<<< HEAD
    public void deleteJobTechStacksByJobId(Integer jobId) {
        String q = "DELETE FROM JobTechStack jts WHERE jts.job.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
=======
    public List<JobTechStack> findAllByJobIdJoinTechStack(Integer jobId) {
        Query query = em.createQuery("select jts from JobTechStack jts join fetch jts.techStack where jts.job.id = :jobId", JobTechStack.class);
        query.setParameter("jobId", jobId);
        return query.getResultList();
>>>>>>> 394ec2d307f4964d5175ebdd4385e69cab668ca9
    }
}

