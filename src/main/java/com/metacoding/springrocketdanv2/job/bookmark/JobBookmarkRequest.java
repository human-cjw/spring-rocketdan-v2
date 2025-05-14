package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.user.User;
import lombok.Data;

public class JobBookmarkRequest {

    @Data
    public static class SaveDTO {
        private Integer jobId;

        public JobBookmark toEntity(User user) {
            return JobBookmark.builder()
                    .user(user)
                    .job(Job.builder().id(this.jobId).build())
                    .build();
        }
    }
}
