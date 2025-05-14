package com.metacoding.springrocketdanv2.workfield;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkFieldService {
    private final WorkFieldRepository workFieldRepository;

    public WorkFieldResponse.ListDTO 목록보기() {
        List<WorkField> workFieldsPS = workFieldRepository.findAll();
        return new WorkFieldResponse.ListDTO(workFieldsPS);
    }
}