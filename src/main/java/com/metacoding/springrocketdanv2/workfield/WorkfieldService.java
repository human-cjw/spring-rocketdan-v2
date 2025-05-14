package com.metacoding.springrocketdanv2.workfield;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkfieldService {
    private final WorkfieldRepository workFieldRepository;
}