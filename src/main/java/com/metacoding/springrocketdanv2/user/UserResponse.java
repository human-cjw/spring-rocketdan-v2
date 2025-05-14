package com.metacoding.springrocketdanv2.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class SessionUserDTO {
        private Integer id;
        private String username;
        private String email;
        private String fileUrl;
        private String userType;
        private Integer companyId;
        private String companyName;

        public SessionUserDTO(Integer id, String username, String email, String fileUrl, String userType, Integer companyId, String companyName) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fileUrl = fileUrl;
            this.userType = userType;
            this.companyId = companyId;
            this.companyName = companyName;
        }
    }
}