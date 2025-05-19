package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.user.User;
import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class SaveDTO {
        private Integer jobId;
        private Integer resumeId;

        public Application toEntity(Integer userId, Integer companyId) {
            return Application.builder()
                    .job(Job.builder().id(this.jobId).build())
                    .company(Company.builder().id(companyId).build())
                    .resume(Resume.builder().id(this.resumeId).build())
                    .user(User.builder().id(userId).build())
                    .status(ApplicationEnum.APPLIED.value)
                    .build();
        }
    }
}
