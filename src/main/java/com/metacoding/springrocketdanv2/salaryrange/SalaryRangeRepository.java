package com.metacoding.springrocketdanv2.salaryrange;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SalaryRangeRepository {
    private final EntityManager em;

    public List<SalaryRange> findAll() {
        String q = "SELECT sr FROM SalaryRange sr";
        return em.createQuery(q, SalaryRange.class).getResultList();
    }
}