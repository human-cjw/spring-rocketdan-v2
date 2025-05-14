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

    public TechStack findById(Integer techStackId) {
        return em.find(TechStack.class, techStackId);


    }

    public TechStack findByName(String name) {
        String sql = "SELECT t FROM TechStack t WHERE t.name = :name";
        List<TechStack> result = em.createQuery(sql, TechStack.class)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);

    }
}