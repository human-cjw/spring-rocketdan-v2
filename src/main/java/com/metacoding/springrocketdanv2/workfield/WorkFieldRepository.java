package com.metacoding.springrocketdanv2.workfield;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkFieldRepository {
    private final EntityManager em;

    public List<WorkField> findAll() {
        String q = "SELECT w FROM WorkField w";
        return em.createQuery(q, WorkField.class).getResultList();
    }

    public Optional<WorkField> findById(Integer id) {
        return Optional.ofNullable(em.find(WorkField.class, id));
    }
}