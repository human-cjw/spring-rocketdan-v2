package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.jobgroup.Jobgroup;
import com.metacoding.springrocketdanv2.jobgroup.JobgroupResponse;
import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStackResponse;
import com.metacoding.springrocketdanv2.salaryrange.Salaryrange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryrangeResponse;
import com.metacoding.springrocketdanv2.techstack.Techstack;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeResponse {

    @Data
    public static class UpdateDTO {
        private Integer id;
        private String title;
        private String summary;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String portfolioUrl;
        private String createdAt;
        private String jobGroupId;
        private String enrollmentDate;
        private String graduationDate;
        private boolean isDefault;
        private String name;
        private String email;
        private List<Certification> certifications; // 다른 테이블에서 가지고 온 것
        private List<Techstack> resumeTechstacks; // 다른 테이블에서 가지고 온 것 (유저가 갖고 있는거)
        private List<Career> careers;
        private List<ResumeTechStackResponse.ResumeTechStackResponseDTO> techStacks;
        private List<GraduationTypeDTO> graduationTypes;
        private List<CareerLevelTypeDTO> careerLevelTypes;
        private List<GenderTypeDTO> genderTypes;
        private List<SalaryrangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs;
        private List<JobgroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs;

        public UpdateDTO(Resume resume,
                         List<Certification> certifications,
                         List<Techstack> resumeTechstacks,
                         String email,
                         String name,
                         List<Career> careers,
                         List<ResumeTechStackResponse.ResumeTechStackResponseDTO> techStacks,
                         List<GraduationTypeDTO> graduationTypes,
                         List<CareerLevelTypeDTO> careerLevelTypes,
                         List<GenderTypeDTO> genderTypes,
                         List<SalaryrangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs,
                         List<JobgroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs
        ) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.summary = resume.getSummary();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.enrollmentDate = resume.getEnrollmentDate();
            this.graduationDate = resume.getGraduationDate();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.portfolioUrl = resume.getPortfolioUrl();
            this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
            this.certifications = certifications;
            this.resumeTechstacks = resumeTechstacks;
            this.email = email;
            this.name = name;
            this.careers = careers;
            this.techStacks = techStacks;
            this.graduationTypes = graduationTypes;
            this.careerLevelTypes = careerLevelTypes;
            this.genderTypes = genderTypes;
            this.isDefault = resume.getIsDefault();
            this.salaryRangeUpdateDTOs = salaryRangeUpdateDTOs;
            this.jobGroupUpdateDTOs = jobGroupUpdateDTOs;
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String summary;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String portfolioUrl;
        private String createdAt;
        private String enrollmentDate;
        private String graduationDate;
        private boolean isDefault;
        private String name;
        private String email;
        private List<Certification> certifications; // 다른 테이블에서 가지고 온 것
        private List<Techstack> resumeTechstacks; // 다른 테이블에서 가지고 온 것 (유저가 갖고 있는거)
        private List<Career> careers;
        private List<ResumeTechStackResponse.ResumeTechStackResponseDTO> techStacks;
        private List<GraduationTypeDTO> graduationTypes;
        private List<CareerLevelTypeDTO> careerLevelTypes;
        private List<GenderTypeDTO> genderTypes;
        private boolean isOwner;
        private String salaryRangeLabel;
        private String jobGroupName;

        public DetailDTO(Resume resume, List<Certification> certifications, List<Techstack> resumeTechstacks,
                         String email, String name, List<Career> careers,
                         List<ResumeTechStackResponse.ResumeTechStackResponseDTO> techStacks,
                         List<GraduationTypeDTO> graduationTypes, List<CareerLevelTypeDTO> careerLevelTypes,
                         List<GenderTypeDTO> genderTypes, Integer userId) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.summary = resume.getSummary();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.enrollmentDate = resume.getEnrollmentDate();
            this.graduationDate = resume.getGraduationDate();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.portfolioUrl = resume.getPortfolioUrl();
            this.createdAt = resume.getCreatedAt().toString().substring(0, 10);
            this.certifications = certifications;
            this.resumeTechstacks = resumeTechstacks;
            this.email = email;
            this.name = name;
            this.careers = careers;
            this.techStacks = techStacks;
            this.graduationTypes = graduationTypes;
            this.careerLevelTypes = careerLevelTypes;
            this.genderTypes = genderTypes;
            this.isDefault = resume.getIsDefault();
            this.isOwner = resume.getUser().getId().equals(userId);
            this.salaryRangeLabel = resume.getSalaryRange().getLabel();
            this.jobGroupName = resume.getJobGroup().getName();
        }
    }

    @Data
    public static class GraduationTypeDTO {
        private String value;
        private Boolean isSelected;

        public GraduationTypeDTO(String value, Boolean isSelected) {
            this.value = value;
            this.isSelected = isSelected;
        }
    }

    @Data
    public static class CareerLevelTypeDTO {
        private String value;
        private Boolean isSelected;

        public CareerLevelTypeDTO(String value, Boolean isSelected) {
            this.value = value;
            this.isSelected = isSelected;
        }
    }

    @Data
    public static class GenderTypeDTO {
        private String value;
        private Boolean isSelected;

        public GenderTypeDTO(String value, Boolean isSelected) {
            this.value = value;
            this.isSelected = isSelected;
        }
    }

    @Data
    public static class ResumeListDTO {
        private boolean isAll;
        private boolean isDefault;
        private List<ResumeItemDTO> resumeItems = new ArrayList<>();

        public ResumeListDTO(List<Resume> resumes, boolean isDefault) {
            if (isDefault) {
                this.isDefault = true;
            } else {
                this.isAll = true;
            }

            for (Resume resume : resumes) {
                this.resumeItems.add(new ResumeItemDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt().toString().substring(0, 10)));
            }
        }

        @Data
        class ResumeItemDTO {
            private Integer id;
            private String title;
            private String createdAt;

            public ResumeItemDTO(Integer id, String title, String createdAt) {
                this.id = id;
                this.title = title;
                this.createdAt = createdAt;
            }
        }
    }

    @Data
    public static class SaveDTO {
        private List<TechStackSaveDTO> techStacks;
        private List<SalaryRangeSaveDTO> salaryRanges;
        private List<JobGroupSaveDTO> jobGroups;

        public SaveDTO(List<Techstack> techstacks,
                       List<Salaryrange> salaryranges,
                       List<Jobgroup> jobgroups) {

            this.techStacks = techstacks.stream()
                    .map(ts -> new TechStackSaveDTO(ts))
                    .collect(Collectors.toList());

            this.salaryRanges = salaryranges.stream()
                    .map(sr -> new SalaryRangeSaveDTO(sr))
                    .collect(Collectors.toList());

            this.jobGroups = jobgroups.stream()
                    .map(jg -> new JobGroupSaveDTO(jg))
                    .collect(Collectors.toList());
        }

        class JobGroupSaveDTO {
            private Integer id;
            private String name;

            public JobGroupSaveDTO(Jobgroup jobGroup) {
                this.id = jobGroup.getId();
                this.name = jobGroup.getName();
            }
        }

        class SalaryRangeSaveDTO {
            private Integer id;
            private Integer minSalary;
            private Integer maxSalary;
            private String label;

            public SalaryRangeSaveDTO(Salaryrange salaryRange) {
                this.id = salaryRange.getId();
                this.minSalary = salaryRange.getMinSalary();
                this.maxSalary = salaryRange.getMaxSalary();
                this.label = salaryRange.getLabel();
            }
        }

        class TechStackSaveDTO {
            private Integer id;
            private String name;

            public TechStackSaveDTO(Techstack techStack) {
                this.id = techStack.getId();
                this.name = techStack.getName();
            }
        }
    }
}



