package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "공고 ID는 필수입니다")
        private Integer jobId;
        @NotNull(message = "이력서 ID는 필수입니다")
        private Integer resumeId;

        public Application toEntity(Integer userId, Integer companyId) {
            return Application.builder()
                    .job(Job.builder().id(this.jobId).build())
                    .company(Company.builder().id(companyId).build())
                    .resume(Resume.builder().id(this.resumeId).build())
                    .user(User.builder().id(userId).build())
                    .status(ApplicationStatusEnum.APPLIED.value)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @NotBlank(message = "상태값은 필수입니다")
        @Pattern(
                regexp = "^(접수|검토|합격|불합격)$",
                message = "상태값은 접수, 검토, 합격, 불합격 중 하나여야 합니다."
        )
        private String status;
    }
}
