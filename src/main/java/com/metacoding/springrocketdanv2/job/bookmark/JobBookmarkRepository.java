package com.metacoding.springrocketdanv2.job.bookmark;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobBookmarkRepository {
    private final EntityManager em;

    public void save(JobBookmark bookmark) {
        em.persist(bookmark);
    }

    public void delete(JobBookmark bookmark) {
        em.remove(bookmark);
    }

    public Optional<JobBookmark> findByUserIdAndJobId(Integer userId, Integer jobId) {
        try {
            Query query = em.createQuery("SELECT jb FROM JobBookmark jb WHERE jb.user.id = :userId AND jb.job.id = :jobId", JobBookmark.class);
            query.setParameter("userId", userId);
            query.setParameter("jobId", jobId);
            return Optional.of((JobBookmark) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public Long countByUserId(Integer userId) {
        return em.createQuery("SELECT COUNT(jb) FROM JobBookmark jb WHERE jb.user.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public List<JobBookmark> findAllByUserId(Integer userId) {
        return em.createQuery("SELECT jb FROM JobBookmark jb JOIN FETCH jb.job j JOIN FETCH j.company c WHERE jb.user.id = :userId ORDER BY jb.id DESC", JobBookmark.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public JobBookmark findById(Integer id) {
        return em.find(JobBookmark.class, id);
    }

    public List<JobBookmark> findJobBookmarksByUserId(Integer userId) {
        String q = "SELECT jb FROM JobBookmark jb " +
                "WHERE jb.user.id = :userId";
        return em.createQuery(q, JobBookmark.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
