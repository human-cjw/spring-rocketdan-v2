package com.metacoding.springrocketdanv2.job;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobRepository {
    private final EntityManager em;

    public List<Job> findAll() {
        String sql = "SELECT * FROM job_tb ORDER BY id DESC";  // 네이티브 SQL 쿼리
        Query query = em.createNativeQuery(sql, Job.class);
        return query.getResultList();
    }

    public Optional<Job> findById(Integer jobId) {
        return Optional.ofNullable(em.find(Job.class, jobId));
    }

    public Job save(Job job) {
        em.persist(job);
        return job;
    }

    public Optional<Job> findByIdJoinJobTechStackJoinTechStack(Integer jobId) {
        String sql = "SELECT j FROM Job j LEFT JOIN FETCH j.jobTechStacks jts LEFT JOIN FETCH jts.techStack WHERE j.id = :jobId";
        Query query = em.createQuery(sql, Job.class);
        query.setParameter("jobId", jobId);

<<<<<<< HEAD
    public List<Job> findJobsByCompanyId(Integer companyId) {
        String q = "SELECT j FROM Job j JOIN FETCH j.jobGroup WHERE j.company.id = :companyId ORDER BY j.createdAt DESC";
        return em.createQuery(q, Job.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    public void deleteJobById(Integer jobId) {
        String q = "DELETE FROM Job j WHERE j.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
=======
        try {
            return Optional.ofNullable((Job) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
>>>>>>> 394ec2d307f4964d5175ebdd4385e69cab668ca9
    }
}
