package com.metacoding.springrocketdanv2.company;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final EntityManager em;

    public Optional<Company> findById(Integer id) {
        Company companyPS = em.find(Company.class, id);
        return Optional.ofNullable(companyPS);
    }

    public List<Company> findAll() {
        String q = "SELECT c FROM Company c ORDER BY c.id DESC";
        return em.createQuery(q, Company.class).getResultList();
    }

    public Company save(Company company) {
        em.persist(company);
        return company;
    }
}
