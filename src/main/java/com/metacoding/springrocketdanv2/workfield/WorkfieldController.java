package com.metacoding.springrocketdanv2.workfield;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorkfieldController {
    private final WorkfieldService workFieldService;
}