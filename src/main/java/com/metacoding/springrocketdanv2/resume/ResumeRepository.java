package com.metacoding.springrocketdanv2.resume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public Optional<Resume> findById(Integer id) {
        return Optional.ofNullable(em.find(Resume.class, id));
    }


    // 오버라이드 이력서 관리 페이지에서 사용
    public List<Resume> findAllByUserId(Integer userId, boolean isDefault) {
        String sql;

        if (isDefault) {
            sql = "SELECT res FROM Resume res WHERE res.user.id = :userId and res.isDefault = true ORDER BY res.id DESC";

        } else {
            sql = "SELECT res FROM Resume res WHERE res.user.id = :userId ORDER BY res.id DESC";
        }

        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Resume> findAllByUserId(Integer userId) {
        String sql;
        sql = "SELECT res FROM Resume res WHERE res.user.id = :userId ORDER BY res.id DESC";
        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Resume findByUserIdAndIsDefaultTrue(Integer userId) {
        String sql = "SELECT res FROM Resume res WHERE res.user.id = :userId AND res.isDefault = true";
        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("userId", userId);

        try {
            return (Resume) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(Integer resumeId) {
        em.createQuery("DELETE FROM Resume r WHERE r.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public Resume save(Resume resume) {
        em.persist(resume);
        return resume;
    }
}
