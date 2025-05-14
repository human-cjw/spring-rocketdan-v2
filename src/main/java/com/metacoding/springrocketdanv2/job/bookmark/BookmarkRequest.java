package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmark;
import com.metacoding.springrocketdanv2.user.User;
import lombok.Data;

public class BookmarkRequest {

    @Data
    public static class SaveDTO {
        private Integer jobId;

        public JobBookmark toEntity(User user) {
            return new JobBookmark(user, Job.builder().id(jobId).build());
        }
    }
}
