package com.metacoding.springrocketdanv2.jobgroup;

import com.metacoding.springrocketdanv2._core.util.Resp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobGroupController {
    private final JobGroupService jobGroupService;

    @GetMapping("/jobGroup")
    public ResponseEntity<?> jobGroupList() {
        JobGroupResponse.ListDTO respDTO = jobGroupService.목록보기();

        log.debug("업무목록" + respDTO);

        return Resp.ok(respDTO);
    }
}
