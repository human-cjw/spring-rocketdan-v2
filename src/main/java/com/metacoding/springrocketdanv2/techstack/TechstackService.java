package com.metacoding.springrocketdanv2.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechstackService {
    private final TechstackRepository techStackRepository;
}