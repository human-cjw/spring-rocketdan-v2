package com.metacoding.springrocketdanv2.job.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechstackRepository {
    private final EntityManager em;
}
