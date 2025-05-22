package com.metacoding.springrocketdanv2.certification;

import lombok.Data;

public class CertificationResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String name;
        private String issuer;
        private String issuedDate;

        public DTO(Certification certification) {
            this.id = certification.getId();
            this.name = certification.getName();
            this.issuer = certification.getIssuer();
            this.issuedDate = certification.getIssuedDate();
        }
    }
}
