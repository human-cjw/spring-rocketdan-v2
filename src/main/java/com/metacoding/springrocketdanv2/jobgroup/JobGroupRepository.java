package com.metacoding.springrocketdanv2.jobgroup;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobGroupRepository {
    private final EntityManager em;

    public List<JobGroup> findAll() {
        String q = "SELECT jg FROM JobGroup jg";
        return em.createQuery(q, JobGroup.class).getResultList();
    }

    public Optional<JobGroup> findByJobGroupId(Integer jobGroupId) {
        return Optional.ofNullable(em.find(JobGroup.class, jobGroupId));
    }
}
