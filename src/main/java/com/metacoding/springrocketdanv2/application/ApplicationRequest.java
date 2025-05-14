package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class SaveDTO {
        private Integer resumeId;
        private Integer companyId;
        @Pattern(regexp = "^(접수|검토|합격|불합격)$", message = "접수|검토|합격|불합격 중 하나로 입력해 주세요")
        private String status;

        public Application toEntity(Integer jobId, Integer userId) {
            return Application.builder()
                    .job(Job.builder().id(jobId).build())
                    .company(Company.builder().id(this.companyId).build())
                    .resume(Resume.builder().id(this.resumeId).build())
                    .user(User.builder().id(userId).build())
                    .status(this.status)
                    .build();
        }
    }
}
