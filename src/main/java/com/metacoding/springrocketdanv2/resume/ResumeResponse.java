package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerResponse;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.certification.CertificationResponse;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.UserResponse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class ResumeResponse {

    @Data
    public static class UpdateDTO {
        private Integer resumeId;
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
        private String createdAt;
        private UserResponse.DTO user;
        private List<CertificationResponse.DTO> certifications;
        private List<CareerResponse.DTO> careers;
        private SalaryRangeResponse.DTO salaryRange;
        private JobGroupResponse.DTO jobGroup;
        private List<TechStackResponse.DTO> techStacks;

        public UpdateDTO(Resume resume, List<Certification> certifications, List<Career> careers) {
            this.resumeId = resume.getId();
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
            this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
            this.user = new UserResponse.DTO(resume.getUser());
            this.certifications = certifications.stream()
                    .map(certification -> new CertificationResponse.DTO(certification))
                    .toList();
            this.careers = careers.stream()
                    .map(career -> new CareerResponse.DTO(career))
                    .toList();
            this.salaryRange = new SalaryRangeResponse.DTO(resume.getSalaryRange());
            this.jobGroup = new JobGroupResponse.DTO(resume.getJobGroup());
            this.techStacks = resume.getResumeTechStacks().stream()
                    .map(resumeTechStack -> new TechStackResponse.DTO(resumeTechStack.getTechStack()))
                    .toList();
        }
    }

    @Data
    public static class DetailDTO {
        private Integer resumeId;
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
            this.resumeId = resume.getId();
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
            private Integer resumeId;
            private String title;
            private String createdAt;
            private Boolean isDefault;

            public ItemDTO(Resume resume) {
                this.resumeId = resume.getId();
                this.title = resume.getTitle();
                this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
                this.isDefault = resume.getIsDefault();
            }
        }
    }


    @Data
    public static class SaveDTO {
        private List<TechStackSaveDTO> techStacks;
        private List<SalaryRangeSaveDTO> salaryRanges;
        private List<JobGroupSaveDTO> jobGroups;

        public SaveDTO(List<TechStack> techStacks,
                       List<SalaryRange> salaryRanges,
                       List<JobGroup> jobGroups) {

            this.techStacks = techStacks.stream()
                    .map(ts -> new TechStackSaveDTO(ts))
                    .collect(Collectors.toList());

            this.salaryRanges = salaryRanges.stream()
                    .map(sr -> new SalaryRangeSaveDTO(sr))
                    .collect(Collectors.toList());

            this.jobGroups = jobGroups.stream()
                    .map(jg -> new JobGroupSaveDTO(jg))
                    .collect(Collectors.toList());
        }

        class JobGroupSaveDTO {
            private Integer id;
            private String name;

            public JobGroupSaveDTO(JobGroup jobGroup) {
                this.id = jobGroup.getId();
                this.name = jobGroup.getName();
            }
        }

        class SalaryRangeSaveDTO {
            private Integer id;
            private Integer minSalary;
            private Integer maxSalary;
            private String label;

            public SalaryRangeSaveDTO(SalaryRange salaryRange) {
                this.id = salaryRange.getId();
                this.minSalary = salaryRange.getMinSalary();
                this.maxSalary = salaryRange.getMaxSalary();
                this.label = salaryRange.getLabel();
            }
        }

        class TechStackSaveDTO {
            private Integer id;
            private String name;

            public TechStackSaveDTO(TechStack techStack) {
                this.id = techStack.getId();
                this.name = techStack.getName();
            }
        }
    }
}



