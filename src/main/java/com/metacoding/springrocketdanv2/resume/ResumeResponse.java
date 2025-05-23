package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerResponse;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.certification.CertificationResponse;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.UserResponse;
import lombok.Data;

import java.util.List;

public class ResumeResponse {

    @Data
    public static class UpdateDTO {
        private String title;
        private String summary;
        private String portfolioUrl;
        private String gender;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String enrollmentDate;
        private String graduationDate;
        private String careerLevel;
        private Boolean isDefault;
        private Integer salaryRangeId;
        private Integer jobGroupId;
        private List<CertificationResponse.DTO> certifications;
        private List<CareerResponse.DTO> careers;
        private List<ResumeTechStackResponse.DTO> resumeTechStacks;

        public UpdateDTO(Resume resume) {
            this.title = resume.getTitle();
            this.summary = resume.getSummary();
            this.portfolioUrl = resume.getPortfolioUrl();
            this.gender = resume.getGender();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.enrollmentDate = resume.getEnrollmentDate();
            this.graduationDate = resume.getGraduationDate();
            this.careerLevel = resume.getCareerLevel();
            this.isDefault = resume.getIsDefault();
            this.salaryRangeId = resume.getSalaryRange().getId();
            this.jobGroupId = resume.getJobGroup().getId();
            this.certifications = resume.getCertifications().stream()
                    .map(certification -> new CertificationResponse.DTO(certification))
                    .toList();
            this.careers = resume.getCareers().stream()
                    .map(career -> new CareerResponse.DTO(career))
                    .toList();
            this.resumeTechStacks = resume.getResumeTechStacks().stream()
                    .map(resumeTechStack -> new ResumeTechStackResponse.DTO(resumeTechStack))
                    .toList();
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String summary;
        private String gender;
        private String careerLevel;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String portfolioUrl;
        private String enrollmentDate;
        private String graduationDate;
        private String createdAt;
        private Boolean isDefault;
        private UserResponse.DTO user;
        private List<CertificationResponse.DTO> certifications;
        private List<CareerResponse.DTO> careers;
        private Boolean isOwner;
        private SalaryRangeResponse.DTO salaryRange;
        private JobGroupResponse.DTO jobGroup;
        private List<TechStackResponse.DTO> techStacks;

        public DetailDTO(Resume resume, List<Certification> certifications, List<Career> careers, Integer sessionUserId) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.summary = resume.getSummary();
            this.gender = resume.getGender();
            this.careerLevel = resume.getCareerLevel();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.portfolioUrl = resume.getPortfolioUrl();
            this.enrollmentDate = resume.getEnrollmentDate();
            this.graduationDate = resume.getGraduationDate();
            this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
            this.isDefault = resume.getIsDefault();
            this.user = new UserResponse.DTO(resume.getUser());
            this.certifications = certifications.stream()
                    .map(certification -> new CertificationResponse.DTO(certification))
                    .toList();
            this.careers = careers.stream()
                    .map(career -> new CareerResponse.DTO(career))
                    .toList();
            this.isOwner = resume.getUser().getId().equals(sessionUserId);
            this.salaryRange = new SalaryRangeResponse.DTO(resume.getSalaryRange());
            this.jobGroup = new JobGroupResponse.DTO(resume.getJobGroup());
            this.techStacks = resume.getResumeTechStacks().stream()
                    .map(resumeTechStack -> new TechStackResponse.DTO(resumeTechStack.getTechStack()))
                    .toList();
        }
    }

    @Data
    public static class ListDTO {
        private List<ItemDTO> resumes;

        public ListDTO(List<Resume> resumes) {
            this.resumes = resumes.stream()
                    .map((resume) -> new ItemDTO(resume))
                    .toList();
        }

        @Data
        class ItemDTO {
            private Integer id;
            private String title;
            private String createdAt;
            private Boolean isDefault;

            public ItemDTO(Resume resume) {
                this.id = resume.getId();
                this.title = resume.getTitle();
                this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
                this.isDefault = resume.getIsDefault();
            }
        }
    }


    @Data
    public static class SaveDTO {
        private Integer id;
        private String title;
        private String summary;
        private String gender;
        private String careerLevel;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String portfolioUrl;
        private String enrollmentDate;
        private String graduationDate;
        private String createdAt;
        private Boolean isDefault;
        private Integer userId;
        private List<CertificationResponse.DTO> certifications;
        private List<CareerResponse.DTO> careers;
        private Integer salaryRangeId;
        private Integer jobGroupId;
        private List<ResumeTechStackResponse.DTO> resumeTechStacks;

        public SaveDTO(Resume resume) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.summary = resume.getSummary();
            this.gender = resume.getGender();
            this.careerLevel = resume.getCareerLevel();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.portfolioUrl = resume.getPortfolioUrl();
            this.enrollmentDate = resume.getEnrollmentDate();
            this.graduationDate = resume.getGraduationDate();
            this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
            this.isDefault = resume.getIsDefault();
            this.userId = resume.getUser().getId();
            this.salaryRangeId = resume.getSalaryRange().getId();
            this.jobGroupId = resume.getJobGroup().getId();
            this.certifications = resume.getCertifications().stream()
                    .map(certification -> new CertificationResponse.DTO(certification))
                    .toList();
            this.careers = resume.getCareers().stream()
                    .map(career -> new CareerResponse.DTO(career))
                    .toList();
            this.resumeTechStacks = resume.getResumeTechStacks().stream()
                    .map(resumeTechStack -> new ResumeTechStackResponse.DTO(resumeTechStack))
                    .toList();
        }
    }
}



