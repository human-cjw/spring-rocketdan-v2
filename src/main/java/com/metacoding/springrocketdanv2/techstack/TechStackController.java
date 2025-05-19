package com.metacoding.springrocketdanv2.techstack;

import com.metacoding.springrocketdanv2._core.util.Resp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TechStackController {
    private final TechStackService techStackService;

    @GetMapping("/techstack")
    public ResponseEntity<?> techStackList() {
        TechStackResponse.ListDTO respDTO = techStackService.목록보기();

        log.debug("기술스택목록" + respDTO);

        return Resp.ok(respDTO);
    }
}