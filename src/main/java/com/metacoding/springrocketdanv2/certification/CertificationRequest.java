package com.metacoding.springrocketdanv2.certification;

import com.metacoding.springrocketdanv2.resume.Resume;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class CertificationRequest {

    @Data
    public static class DTO {
        @NotBlank(message = "자격증 이름은 필수입니다.")
        private String name;

        @NotBlank(message = "발급 기관은 필수입니다.")
        private String issuer;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "발급일은 yyyy-MM-dd 형식이어야 합니다."
        )
        private String issuedDate;

        public Certification toEntity(Resume resume) {
            return Certification.builder()
                    .name(name)
                    .issuer(issuer)
                    .issuedDate(issuedDate)
                    .resume(resume)
                    .build();
        }
    }

}
