package com.metacoding.springrocketdanv2.workfield;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkFieldRepository {
    private final EntityManager em;

    public List<WorkField> findAll() {
        String q = "SELECT w FROM WorkField w";
        return em.createQuery(q, WorkField.class).getResultList();
    }

    public WorkField findById(Integer id) {
        return em.find(WorkField.class, id);
    }

    public WorkField save(WorkField workField) {
        em.persist(workField);
        return workField;
    }
}