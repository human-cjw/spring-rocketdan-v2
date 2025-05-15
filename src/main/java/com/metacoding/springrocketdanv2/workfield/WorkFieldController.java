package com.metacoding.springrocketdanv2.workfield;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorkFieldController {
    private final WorkFieldService workFieldService;

    @GetMapping("/api/workFields")
    public void getWorkFields() {
        WorkFieldResponse.ListDTO respDTO = workFieldService.목록보기();
        log.debug("workFields", respDTO);
    }
}