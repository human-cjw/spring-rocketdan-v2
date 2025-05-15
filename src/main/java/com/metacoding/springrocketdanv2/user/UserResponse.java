package com.metacoding.springrocketdanv2.user;

import lombok.Builder;
import lombok.Data;

public class UserResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String username;
        private String email;
        private String fileUrl;
        private String userType;
        private Integer companyId;
        private String createdAt;

        public DTO(User user) {
            this.id = user.getId();
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
}