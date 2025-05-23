package com.metacoding.springrocketdanv2.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TechStackRepository {
    private final EntityManager em;

    public List<TechStack> findAll() {
        String q = "SELECT t FROM TechStack t";
        return em.createQuery(q, TechStack.class).getResultList();
    }

    public Optional<TechStack> findByTechStackId(Integer techStackId) {
        return Optional.ofNullable(em.find(TechStack.class, techStackId));
    }
}