package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class JobBookmarkRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "job의 id가 전달되어야 합니다")
        private Integer jobId;

        public JobBookmark toEntity(Integer userId) {
            return JobBookmark.builder()
                    .user(User.builder().id(userId).build())
                    .job(Job.builder().id(this.jobId).build())
                    .build();
        }
    }
}
