package com.metacoding.springrocketdanv2.application;

import lombok.Data;

import java.util.List;

public class ApplicationResponse {

    @Data
    public static class SaveDTO {
        private Integer applicationId;
        private String status;
        private String createdAt;
        private Integer jobId;
        private Integer resumeId;

        public SaveDTO(Application application) {
            this.applicationId = application.getId();
            this.status = application.getStatus();
            this.createdAt = application.getCreatedAt().toString().substring(0, 10);
            this.jobId = application.getJob().getId();
            this.resumeId = application.getResume().getId();
        }
    }

    @Data
    public static class ListForUserDTO {
        List<ItemDTO> applications;

        public ListForUserDTO(List<Application> applications) {
            this.applications = applications.stream()
                    .map(application -> new ItemDTO(application))
                    .toList();
        }

        @Data
        class ItemDTO {
            private Integer applicationId;
            private String status;
            private String createdAt;
            private Integer jobId;
            private String jobTitle;
            private String companyName;
            private String careerLevel;
            private Integer resumeId;

            public ItemDTO(Application application) {
                this.applicationId = application.getId();
                this.status = application.getStatus();
                this.createdAt = application.getCreatedAt().toString().substring(0, 10);
                this.jobId = application.getJob().getId();
                this.jobTitle = application.getJob().getTitle();
                this.companyName = application.getCompany().getNameKr();
                this.careerLevel = application.getJob().getCareerLevel();
                this.resumeId = application.getResume().getId();
            }
        }
    }
}
