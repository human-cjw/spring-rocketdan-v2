package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2.application.Application;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class UserResponse {

    @Data
    public static class DTO {
        private Integer userId;
        private String username;
        private String email;
        private String fileUrl;
        private String userType;
        private Integer companyId;
        private String createdAt;

        public DTO(User user) {
            this.userId = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.fileUrl = user.getFileUrl();
            this.userType = user.getUserType();
            this.companyId = user.getCompanyId();
            this.createdAt = user.getCreatedAt().toString();
        }
    }

    @Data
    public static class TokenDTO {
        private String accessToken; // jwt
        private String refreshToken;

        @Builder
        public TokenDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
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