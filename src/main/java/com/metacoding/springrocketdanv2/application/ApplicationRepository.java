package com.metacoding.springrocketdanv2.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplicationRepository {
    private final EntityManager em;

    public List<Application> findAllByUserIdAndStatus(Integer userId, String status) {
        String q;
        Query query;
        if (status == null) {
            q = """
                        SELECT a FROM Application a
                        JOIN FETCH a.job
                        JOIN FETCH a.resume
                        JOIN FETCH a.company
                        JOIN FETCH a.user
                        WHERE a.user.id = :userId
                    """;
            query = em.createQuery(q, Application.class)
                    .setParameter("userId", userId);
        } else {
            q = """
                        SELECT a FROM Application a
                        JOIN FETCH a.job
                        JOIN FETCH a.resume
                        JOIN FETCH a.company
                        JOIN FETCH a.user
                        WHERE a.user.id = :userId
                        AND a.status = :status
                    """;
            query = em.createQuery(q, Application.class)
                    .setParameter("userId", userId)
                    .setParameter("status", status);
        }
        return query.getResultList();
    }

    public Application save(Application application) {
        em.persist(application);
        return application;
    }


    public List<Application> findAllByJobIdJoinFetchAll(Integer jobId, String status) {
        String q = """
                    SELECT a
                    FROM Application a
                    JOIN FETCH a.resume r
                    JOIN FETCH a.user u
                    JOIN FETCH a.job j
                    WHERE a.job.id = :jobId
                    AND a.status = :status
                """;
        return em.createQuery(q, Application.class)
                .setParameter("jobId", jobId)
                .setParameter("status", status)
                .getResultList();
    }

    public Optional<Application> findByApplicationId(Integer id) {
        return Optional.ofNullable(em.find(Application.class, id));
    }

    public Optional<Application> findByJobIdAndUserId(Integer jobId, Integer userId) {
        String q = "SELECT a FROM Application a WHERE a.job.id = :jobId AND a.user.id = :userId";
        Query query = em.createQuery(q, Application.class)
                .setParameter("jobId", jobId)
                .setParameter("userId", userId);
        try {
            return Optional.ofNullable((Application) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public void deleteByJobId(Integer jobId) {
        String q = "DELETE FROM Application a WHERE a.job.id = :jobId";
        em.createQuery(q)
                .setParameter("jobId", jobId)
                .executeUpdate();
    }

    public Optional<Application> findByApplicationIdJoinResumeAndUser(Integer applicationId) {
        String q = """
                    SELECT a
                    FROM Application a
                    JOIN FETCH a.resume r
                    JOIN FETCH a.user u
                    WHERE a.id = :applicationId
                """;

        Query query = em.createQuery(q, Application.class)
                .setParameter("applicationId", applicationId);

        try {
            return Optional.ofNullable((Application) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Application> findByApplicationIdJoinJobAndCompany(Integer applicationId) {
        String q = """
                    SELECT a
                    FROM Application a
                    JOIN FETCH a.job j
                    JOIN FETCH a.company c
                    WHERE a.id = :applicationId
                """;

        Query query = em.createQuery(q, Application.class)
                .setParameter("applicationId", applicationId);

        try {
            return Optional.ofNullable((Application) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public void deleteByResumeId(Integer resumeId) {
        String q = "DELETE FROM Application a WHERE a.resume.id = :resumeId";
        em.createQuery(q)
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }
}