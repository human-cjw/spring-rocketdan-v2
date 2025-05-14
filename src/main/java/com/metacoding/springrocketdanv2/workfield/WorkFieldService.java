package com.metacoding.springrocketdanv2.workfield;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkFieldService {
    private final WorkFieldRepository workFieldRepository;
}