package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final ResumeTechStackRepository resumeTechStackRepository;


}