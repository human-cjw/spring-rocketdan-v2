package com.metacoding.springrocketdanv2.career;

import com.metacoding.springrocketdanv2.resume.Resume;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class CareerRequest {

    @Data
    public static class DTO {
        @NotBlank(message = "회사 이름은 필수입니다.")
        private String companyName;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "시작일은 yyyy-MM-dd 형식이어야 합니다."
        )
        private String startDate;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "시작일은 yyyy-MM-dd 형식이어야 합니다."
        )
        private String endDate;

        public Career toEntity(Resume resume) {
            return Career.builder()
                    .companyName(companyName)
                    .startDate(startDate)
                    .endDate(endDate)
                    .resume(resume)
                    .build();
        }
    }
}
