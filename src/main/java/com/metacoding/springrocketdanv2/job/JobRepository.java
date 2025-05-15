package com.metacoding.springrocketdanv2.job;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobRepository {
    private final EntityManager em;

    public List<Job> findAll() {
        String sql = "SELECT * FROM job_tb ORDER BY id DESC";  // 네이티브 SQL 쿼리
        Query query = em.createNativeQuery(sql, Job.class);
        return query.getResultList();
    }

    public Job findById(Integer id) {
        return em.find(Job.class, id);
    }

    public void save(Job job) {
        em.persist(job);
    }

    public Job findByIdJoinJobTechStackJoinTechStack(Integer jobId) {
        String sql = "SELECT j FROM Job j JOIN FETCH j.jobTechStacks jts JOIN FETCH jts.techStack WHERE j.id = :jobId";
        Query query = em.createQuery(sql, Job.class);  // createQuery를 사용해야 합니다.
        query.setParameter("jobId", jobId);  // 파라미터 설정
        return (Job) query.getSingleResult();
    }

    public List<Job> findJobsByCompanyId(Integer companyId) {
        String q = "SELECT j FROM Job j JOIN FETCH j.jobGroup WHERE j.company.id = :companyId ORDER BY j.createdAt DESC";
        return em.createQuery(q, Job.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    public void deleteJobById(Integer jobId) {
        String q = "DELETE FROM Job j WHERE j.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
    }
}
