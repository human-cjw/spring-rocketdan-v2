package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class JobBookmarkResponse {

    @Data
    public static class JobListWithBookmarkDTO {
        private Integer id;
        private String title;
        private String nameKr;
        private String careerLevel;
        private boolean isBookmarked;

        public JobListWithBookmarkDTO(Job job, boolean isBookmarked) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.nameKr = job.getCompany().getNameKr();
            this.careerLevel = job.getCareerLevel();
            this.isBookmarked = isBookmarked;
        }
    }

    @Data
    public static class BookmarkListDTO {
        private Integer id;
        private Integer jobId;
        private String title;
        private String companyName;
        private String careerLevel;
        private String employmentType;
        private String logoImageUrl;
        private String createdAt;
        private List<String> techStackList;

        public BookmarkListDTO(JobBookmark bookmark) {
            this.id = bookmark.getId();
            this.jobId = bookmark.getJob().getId();
            this.title = bookmark.getJob().getTitle();
            this.companyName = bookmark.getJob().getCompany().getNameKr();
            this.careerLevel = bookmark.getJob().getCareerLevel();
            this.employmentType = bookmark.getJob().getEmploymentType();
            this.logoImageUrl = bookmark.getJob().getCompany().getLogoImageUrl();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            this.createdAt = bookmark.getCreatedAt().toLocalDateTime().format(formatter);
            this.techStackList = bookmark.getJob().getJobTechStacks().stream()
                    .map(ts -> ts.getTechStack().getName())
                    .toList();
        }
    }
}
