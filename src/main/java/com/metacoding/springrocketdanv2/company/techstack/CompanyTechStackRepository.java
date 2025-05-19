package com.metacoding.springrocketdanv2.company.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyTechStackRepository {
    private final EntityManager em;

    public void save(CompanyTechStack cts) {
        em.persist(cts);
    }

    public void deleteByCompanyId(Integer companyId) {
        String q = "DELETE FROM CompanyTechStack cts WHERE cts.company.id = :companyId";
        em.createQuery(q)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

}
