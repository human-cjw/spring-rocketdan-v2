package com.metacoding.springrocketdanv2.love;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoveRepository {
    private final EntityManager em;
}
