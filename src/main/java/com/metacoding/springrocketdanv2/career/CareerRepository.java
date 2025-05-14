package com.metacoding.springrocketdanv2.career;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareerRepository {
    private final EntityManager em;

    public List<Career> findCareersByResumeId(Integer resumeId) {
        String q = "SELECT car FROM Career car WHERE car.resume.id = :resumeId";
        return em.createQuery(q, Career.class)
                .setParameter("resumeId", resumeId)
                .getResultList();
    }

    public void deleteByResumeId(Integer resumeId) {
        em.createQuery("DELETE FROM Career c WHERE c.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public void save(Career career) {
        em.persist(career);
    }

    public Career findByResumeId(Integer resumeId) {
        String q = "SELECT car FROM Career car WHERE car.resume.id = :resumeId";
        try {
            return em.createQuery(q, Career.class)
                    .setParameter("resumeId", resumeId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
