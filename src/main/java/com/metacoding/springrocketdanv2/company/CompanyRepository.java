package com.metacoding.springrocketdanv2.company;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final EntityManager em;

    public Company findById(Integer id) {
        return em.find(Company.class, id); // Lazy loading
    }

    public List<Company> findAll() {
        String q = "SELECT c FROM Company c ORDER BY c.id DESC";
        return em.createQuery(q, Company.class).getResultList();
    }

    public Company save(Company company) {
        em.persist(company);
        return company;
    }

    public Company findByUserId(Integer userId) {
        String q = "SELECT c FROM Company c WHERE c.user.id = :userId";
        return em.createQuery(q, Company.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

}
