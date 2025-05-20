package com.metacoding.springrocketdanv2.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechStackRepository {
    private final EntityManager em;

    public List<TechStack> findAll() {
        String q = "SELECT t FROM TechStack t";
        return em.createQuery(q, TechStack.class).getResultList();
    }
}