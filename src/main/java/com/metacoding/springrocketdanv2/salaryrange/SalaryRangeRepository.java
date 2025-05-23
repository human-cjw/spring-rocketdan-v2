package com.metacoding.springrocketdanv2.salaryrange;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SalaryRangeRepository {
    private final EntityManager em;

    public List<SalaryRange> findAll() {
        String q = "SELECT sr FROM SalaryRange sr";
        return em.createQuery(q, SalaryRange.class).getResultList();
    }

    public Optional<SalaryRange> findBySalaryRangeId(Integer salaryRangeId) {
        return Optional.ofNullable(em.find(SalaryRange.class, salaryRangeId));
    }
}