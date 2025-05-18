package com.metacoding.springrocketdanv2.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplicationRepository {
    private final EntityManager em;

    public List<Application> findByUserId(Integer userId) {
        String q = """
                    SELECT a
                    FROM Application a
                    JOIN FETCH a.job
                    JOIN FETCH a.resume
                    JOIN FETCH a.company
                    JOIN FETCH a.user
                    WHERE a.user.id = :userId
                """;
        return em.createQuery(q, Application.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Application> findByUserIdStatus(Integer userId, String status) {
        String q = """
                    SELECT a FROM Application a
                    JOIN FETCH a.job
                    JOIN FETCH a.resume
                    JOIN FETCH a.company
                    JOIN FETCH a.user
                    WHERE a.user.id = :userId
                    AND (:status IS NULL OR a.status = :status)
                """;
        return em.createQuery(q, Application.class)
                .setParameter("userId", userId)
                .setParameter("status", status)
                .getResultList();
    }

    public void save(Application application) {
        em.persist(application);
    }


    public List<Application> findByJobId(Integer jobId, String status) {
        String q = """
                    SELECT a
                    FROM Application a
                    WHERE a.job.id = :jobId
                    AND (:status IS NULL OR a.status = :status)
                """;
        return em.createQuery(q, Application.class)
                .setParameter("jobId", jobId)
                .setParameter("status", status)
                .getResultList();
    }


    public void updateResumeNullByResumeId(Integer resumeId) {
        em.createQuery("UPDATE Application a SET a.resume = null, a.user = null WHERE a.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public Application findById(Integer id) {
        return em.find(Application.class, id);
    }

    public Application findByCompanyIdWithUserId(Integer companyId, Integer userId) {
        String q = """
                    SELECT a
                    FROM Application a
                    WHERE a.company.id = :companyId
                    AND a.user.id = :userId
                """;
        Query query = em.createQuery(q, Application.class);
        query.setParameter("companyId", companyId);
        query.setParameter("userId", userId);

        try {
            return (Application) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Application findByUserIdAndJobId(Integer userId, Integer jobId) {
        String q = """
                    SELECT a
                    FROM Application a
                    JOIN FETCH a.job
                    JOIN FETCH a.resume
                    JOIN FETCH a.company
                    WHERE a.user.id = :userId
                        AND a.job.id = :jobId
                """;

        try {
            return em.createQuery(q, Application.class)
                    .setParameter("userId", userId)
                    .setParameter("jobId", jobId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public Application findByJobIdWithUserId(Integer jobId, Integer userId) {
        String q = "SELECT a FROM Application a WHERE a.job.id = :jobId AND a.user.id = :userId";
        try {
            return (Application) em.createQuery(q, Application.class)
                    .setParameter("jobId", jobId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Application> findAllByResumeId(Integer resumeId) {
        String q = "SELECT a FROM Application a WHERE a.resume.id = :resumeId";
        return em.createQuery(q, Application.class)
                .setParameter("resumeId", resumeId)
                .getResultList();
    }

    public void deleteByJobId(Integer jobId) {
        String q = "DELETE FROM Application a WHERE a.job.id = :jobId";
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
}