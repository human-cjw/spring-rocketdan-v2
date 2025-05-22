package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import lombok.Data;

import java.util.List;

public class JobBookmarkResponse {

    @Data
    public static class ListDTO {
        private List<ItemDTO> jobBookmarks;
        private Integer jobBookmarkCount;

        public ListDTO(List<JobBookmark> jobBookmarks) {
            this.jobBookmarks = jobBookmarks.stream()
                    .map(jobBookmark -> new ItemDTO(jobBookmark, jobBookmark.getJob()))
                    .toList();
            this.jobBookmarkCount = jobBookmarks.size();
        }

        @Data
        class ItemDTO {
            private Integer id;
            private String jobTitle;
            private String jobCareerLevel;
            private String jobEmploymentType;
            private String jobCreatedAt;
            private List<TechStackResponse.DTO> techStacks;
            private String companyName;
            private String companyLogoImageUrl;

            public ItemDTO(JobBookmark jobBookmark, Job job) {
                this.id = jobBookmark.getId();
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
        private Integer id;
        private Integer jobBookmarkCount;

        public SaveDTO(JobBookmark jobBookmark, Integer jobBookmarkCount) {
            this.id = jobBookmark.getId();
            this.jobBookmarkCount = jobBookmarkCount;
        }
    }

}
