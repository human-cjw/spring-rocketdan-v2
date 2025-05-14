package com.metacoding.springrocketdanv2.resume.bookmark;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeBookmarkRepository {
    private final EntityManager em;

    public void deleteByResumeId(Integer resumeId) {
        em.createQuery("DELETE FROM ResumeBookmark rb WHERE rb.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public List<ResumeBookmark> findAllByResumeId(Integer resumeId) {
        String q = "SELECT rb FROM ResumeBookmark rb WHERE rb.resume.id = :resumeId";
        return em.createQuery(q, ResumeBookmark.class)
                .setParameter("resumeId", resumeId)
                .getResultList();
    }
}