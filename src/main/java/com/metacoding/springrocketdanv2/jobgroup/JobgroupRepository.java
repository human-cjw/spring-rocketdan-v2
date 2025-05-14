package com.metacoding.springrocketdanv2.jobgroup;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobgroupRepository {
    private final EntityManager em;

    public List<Jobgroup> findAll() {
        String q = "SELECT jg FROM Jobgroup jg";
        return em.createQuery(q, Jobgroup.class).getResultList();
    }
}
