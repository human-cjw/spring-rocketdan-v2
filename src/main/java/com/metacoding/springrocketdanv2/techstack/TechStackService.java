package com.metacoding.springrocketdanv2.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final TechStackRepository techStackRepository;

    public TechStackResponse.ListDTO 목록보기() {
        List<TechStack> techStacksPS = techStackRepository.findAll();

        return new TechStackResponse.ListDTO(techStacksPS);
    }
}