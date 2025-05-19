package com.metacoding.springrocketdanv2.resume.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeTechStackRepository {
    private final EntityManager em;

    public void deleteByResumeId(Integer resumeId) {
        em.createQuery("DELETE FROM ResumeTechStack r WHERE r.resume.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public ResumeTechStack save(ResumeTechStack resumeTechStack) {
        em.persist(resumeTechStack);
        return resumeTechStack;
    }

}