package com.metacoding.springrocketdanv2.application;

import lombok.Data;

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
    public static class UpdateDTO {
        private Integer applicationId;
        private String status;

        public UpdateDTO(Application application) {
            this.applicationId = application.getId();
            this.status = application.getStatus();
        }
    }
}
