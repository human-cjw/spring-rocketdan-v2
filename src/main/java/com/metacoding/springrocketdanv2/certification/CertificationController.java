package com.metacoding.springrocketdanv2.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;
}
