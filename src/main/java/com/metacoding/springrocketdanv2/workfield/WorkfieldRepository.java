package com.metacoding.springrocketdanv2.workfield;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkfieldRepository {
    private final EntityManager em;

    public Workfield findByName(String name) {
        String q = "SELECT w FROM Workfield w WHERE w.name = :name";
        List<Workfield> result = em.createQuery(q, Workfield.class)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public String findNameById(Integer id) {
        return em.find(Workfield.class, id).getName();
    }

    public List<Workfield> findAll() {
        String q = "SELECT w FROM Workfield w";
        return em.createQuery(q, Workfield.class).getResultList();
    }


    public Workfield save(Workfield workField) {
        em.persist(workField);
        return workField;
    }

    public Workfield findById(Integer id) {
        return em.find(Workfield.class, id);
    }
}