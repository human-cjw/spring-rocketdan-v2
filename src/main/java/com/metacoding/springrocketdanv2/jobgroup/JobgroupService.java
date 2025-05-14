package com.metacoding.springrocketdanv2.jobgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobgroupService {
    private final JobgroupRepository jobGroupRepository;
}
