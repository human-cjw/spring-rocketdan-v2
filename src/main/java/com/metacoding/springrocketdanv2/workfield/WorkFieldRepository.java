package com.metacoding.springrocketdanv2.workfield;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkFieldRepository {
    private final EntityManager em;

    public WorkField findByName(String name) {
        String q = "SELECT w FROM WorkField w WHERE w.name = :name";
        List<WorkField> result = em.createQuery(q, WorkField.class)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public String findNameById(Integer id) {
        return em.find(WorkField.class, id).getName();
    }

    public List<WorkField> findAll() {
        String q = "SELECT w FROM WorkField w";
        return em.createQuery(q, WorkField.class).getResultList();
    }


    public WorkField save(WorkField workField) {
        em.persist(workField);
        return workField;
    }

    public WorkField findById(Integer id) {
        return em.find(WorkField.class, id);
    }
}