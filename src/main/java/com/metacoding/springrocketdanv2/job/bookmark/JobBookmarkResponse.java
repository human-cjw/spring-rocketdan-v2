package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import lombok.Data;

import java.util.List;

public class JobBookmarkResponse {

    @Data
    public static class ListDTO {
        private List<ItemDTO> jobs;
        private Integer jobBookmarkCount;

        public ListDTO(List<JobBookmark> jobBookmarks) {
            this.jobs = jobBookmarks.stream()
                    .map(jobBookmark -> new ItemDTO(jobBookmark.getId(), jobBookmark.getJob()))
                    .toList();
            this.jobBookmarkCount = jobBookmarks.size();
        }

        @Data
        class ItemDTO {
            private Integer jobBookmarkId;
            private String jobTitle;
            private String jobCareerLevel;
            private String jobEmploymentType;
            private String jobCreatedAt;
            private List<TechStackResponse.DTO> techStacks;
            private String companyName;
            private String companyLogoImageUrl;

            public ItemDTO(Integer jobBookmarkId, Job job) {
                this.jobBookmarkId = jobBookmarkId;
                this.jobTitle = job.getTitle();
                this.jobCareerLevel = job.getCareerLevel();
                this.jobEmploymentType = job.getEmploymentType();
                this.jobCreatedAt = job.getCreatedAt().toString().substring(0, 10);
                this.techStacks = job.getJobTechStacks().stream()
                        .map(jobTechStack -> new TechStackResponse.DTO(jobTechStack.getTechStack()))
                        .toList();
                this.companyName = job.getCompany().getNameKr();
                this.companyLogoImageUrl = job.getCompany().getLogoImageUrl() != null ? job.getCompany().getLogoImageUrl() : "";
            }
        }
    }

    @Data
    public static class SaveDTO {
        private Integer jobBookmarkId;
        private Integer jobBookmarkCount;

        public SaveDTO(Integer jobBookmarkId, Integer jobBookmarkCount) {
            this.jobBookmarkId = jobBookmarkId;
            this.jobBookmarkCount = jobBookmarkCount;
        }
    }

}
