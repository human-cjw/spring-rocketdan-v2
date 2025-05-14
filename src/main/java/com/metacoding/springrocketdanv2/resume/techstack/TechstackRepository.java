package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStack;
import com.metacoding.springrocketdanv2.techstack.Techstack;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechstackRepository {
    private final EntityManager em;

    public List<Techstack> findAllByResumeIdWithTechStack(Integer resumeId) {
        String q = "SELECT r.techStack FROM ResumeTechStack r WHERE r.resume.id = :resumeId";
        return em.createQuery(q, Techstack.class)

                .setParameter("resumeId", resumeId)
                .getResultList();
    }

    public List<ResumeTechStack> findAllByResumeId(Integer resumeId) {
        String q = "SELECT r FROM ResumeTechStack r WHERE r.resume.id = :resumeId";
        return em.createQuery(q, ResumeTechStack.class)
                .setParameter("resumeId", resumeId)
                .getResultList();
    }

    public void deleteByResumeId(Integer resumeId) {
        em.createQuery("DELETE FROM ResumeTechStack r WHERE r.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public void save(ResumeTechStack resumeTechStack) {
        em.persist(resumeTechStack);
    }

}