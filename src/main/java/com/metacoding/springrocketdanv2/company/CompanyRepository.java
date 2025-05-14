package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.job.Job;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final EntityManager em;

    public Company findById(Integer id) {
        return em.find(Company.class, id); // Lazy loading
    }

    public List<Company> findAll() {
        String q = "SELECT c FROM Company c ORDER BY c.id DESC";
        return em.createQuery(q, Company.class).getResultList();
    }

    public Company save(Company company) {
        em.persist(company);
        return company;
    }

    public Company findByUserId(Integer userId) {
        String q = "SELECT c FROM Company c WHERE c.user.id = :userId";
        return em.createQuery(q, Company.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public List<Job> findJobsByCompanyId(Integer companyId) {
        String q = "SELECT j FROM Job j JOIN FETCH j.jobGroup WHERE j.company.id = :companyId ORDER BY j.createdAt DESC";
        return em.createQuery(q, Job.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    public void deleteApplicationsByJobId(Integer jobId) {
        String q = "DELETE FROM Application a WHERE a.job.id = :jobId AND a.resume IS NOT NULL";
        em.createQuery(q)
                .setParameter("jobId", jobId)
                .executeUpdate();
    }

    public List<Application> findApplicationsByJobIdWhereResumeNotNull(Integer jobId) {
        String q = "SELECT a FROM Application a WHERE a.job.id = :jobId AND a.resume IS NOT NULL ORDER BY a.id DESC";
        return em.createQuery(q, Application.class)
                .setParameter("jobId", jobId)
                .getResultList();
    }

    public void deleteJobBookmarksByJobId(Integer jobId) {
        String q = "DELETE FROM JobBookmark jb WHERE jb.job.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
    }

    public void deleteJobTechStacksByJobId(Integer jobId) {
        String q = "DELETE FROM JobTechStack jts WHERE jts.job.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
    }

    public void deleteJobById(Integer jobId) {
        String q = "DELETE FROM Job j WHERE j.id = :jobId";
        em.createQuery(q).setParameter("jobId", jobId).executeUpdate();
    }
}
