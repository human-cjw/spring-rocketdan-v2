package com.metacoding.springrocketdanv2.jobgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobGroupService {
    private final JobGroupRepository jobGroupRepository;

    public JobGroupResponse.ListDTO 목록보기() {
        List<JobGroup> jobGroupsPS = jobGroupRepository.findAll();

        return new JobGroupResponse.ListDTO(jobGroupsPS);
    }
}
