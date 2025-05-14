package com.metacoding.springrocketdanv2.salaryrange;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SalaryrangeRepository {
    private final EntityManager em;

    public List<Salaryrange> findAll() {
        String q = "SELECT sr FROM Salaryrange sr";
        return em.createQuery(q, Salaryrange.class).getResultList();
    }
}