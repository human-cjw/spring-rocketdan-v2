package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.companyTechStack.CompanyTechStack;
import com.metacoding.springrocketdanv2.techstack.Techstack;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechstackRepository {
    private final EntityManager em;

    public List<Techstack> findByCompanyId(Integer companyId) {
        String q = "SELECT cts.techStack FROM Techstack cts WHERE cts.company.id = :companyId";
        return em.createQuery(q, Techstack.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    public void save(CompanyTechStack cts) {
        em.persist(cts);
    }

    public void deleteByCompanyId(Integer companyId) {
        String q = "DELETE FROM Techstack cts WHERE cts.company.id = :companyId";
        em.createQuery(q)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

}
