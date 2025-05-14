package com.metacoding.springrocketdanv2.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechstackRepository {
    private final EntityManager em;

    public List<Techstack> findAll() {
        String q = "SELECT t FROM Techstack t";
        return em.createQuery(q, Techstack.class).getResultList();
    }

    public Techstack findById(Integer techStackId) {
        return em.find(Techstack.class, techStackId);


    }

    public Techstack findByName(String name) {
        String sql = "SELECT t FROM Techstack t WHERE t.name = :name";
        List<Techstack> result = em.createQuery(sql, Techstack.class)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);

    }
}