package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkFieldResponse;
import lombok.Data;

import java.util.List;

public class CompanyResponse {

    @Data
    public static class JobListDTO {
        private List<ItemDTO> jobs;

        public JobListDTO(List<Job> jobs) {
            this.jobs = jobs.stream()
                    .map((job -> new ItemDTO(job)))
                    .toList();
        }

        @Data
        class ItemDTO {
            private Integer jobId;
            private String title;
            private String careerLevel;
            private String createdAt;
            private JobGroupResponse.DTO jobGroup;

            public ItemDTO(Job job) {
                this.jobId = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
                this.createdAt = job.getCreatedAt().toString().substring(0, 10);
                this.jobGroup = new JobGroupResponse.DTO(job.getJobGroup());
            }
        }
    }

    @Data
    public static class UpdateDTO {
        private Integer companyId;
        private String nameKr;
        private String nameEn;
        private String ceo;
        private String businessNumber;
        private String email;
        private String phone;
        private String address;
        private String introduction;
        private String oneLineIntro;
        private String homepageUrl;
        private String logoImageUrl;
        private String infoImageUrl;
        private String contactManager;
        private String startDate;
        private String createdAt;
        private Integer workFieldId;
        private List<Integer> techStackIds;

        public UpdateDTO(Company company) {
            this.companyId = company.getId();
            this.nameKr = company.getNameKr();
            this.nameEn = company.getNameEn();
            this.ceo = company.getCeo();
            this.businessNumber = company.getBusinessNumber();
            this.email = company.getEmail();
            this.phone = company.getPhone();
            this.address = company.getAddress();
            this.introduction = company.getIntroduction();
            this.oneLineIntro = company.getOneLineIntro();
            this.homepageUrl = company.getHomepageUrl();
            this.logoImageUrl = company.getLogoImageUrl();
            this.infoImageUrl = company.getInfoImageUrl();
            this.contactManager = company.getContactManager();
            this.startDate = company.getStartDate();
            this.createdAt = company.getCreatedAt().toString().substring(0, 10);
            this.workFieldId = company.getWorkField().getId();
            this.techStackIds = company.getCompanyTechStacks().stream()
                    .map(companyTechStack -> companyTechStack.getTechStack().getId())
                    .toList();
        }
    }

    @Data
    public static class SaveDTO {
        private Integer companyId;
        private String nameKr;
        private String nameEn;
        private String ceo;
        private String businessNumber;
        private String email;
        private String phone;
        private String address;
        private String introduction;
        private String oneLineIntro;
        private String homepageUrl;
        private String logoImageUrl;
        private String infoImageUrl;
        private String contactManager;
        private String startDate;
        private String createdAt;
        private Integer workFieldId;
        private List<Integer> techStackIds;
        private UserResponse.TokenDTO token;

        public SaveDTO(Company company, UserResponse.TokenDTO token) {
            this.companyId = company.getId();
            this.nameKr = company.getNameKr();
            this.nameEn = company.getNameEn();
            this.ceo = company.getCeo();
            this.businessNumber = company.getBusinessNumber();
            this.email = company.getEmail();
            this.phone = company.getPhone();
            this.address = company.getAddress();
            this.introduction = company.getIntroduction();
            this.oneLineIntro = company.getOneLineIntro();
            this.homepageUrl = company.getHomepageUrl();
            this.logoImageUrl = company.getLogoImageUrl();
            this.infoImageUrl = company.getInfoImageUrl();
            this.contactManager = company.getContactManager();
            this.startDate = company.getStartDate();
            this.createdAt = company.getCreatedAt().toString().substring(0, 10);
            this.workFieldId = company.getWorkField().getId();
            this.techStackIds = company.getCompanyTechStacks().stream()
                    .map(companyTechStack -> companyTechStack.getTechStack().getId())
                    .toList();
            this.token = token;
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String nameKr;
        private String nameEn;
        private String ceo;
        private String businessNumber;
        private String email;
        private String phone;
        private String address;
        private String introduction;
        private String oneLineIntro;
        private String homepageUrl;
        private String logoImageUrl;
        private String infoImageUrl;
        private String contactManager;
        private String startDate;
        private String createdAt;
        private WorkFieldResponse.DTO workField; // ManyToOne → 이름만
        private List<TechStackResponse.DTO> techStacks; // OneToMany → 이름만
        private boolean isOwner;

        public DetailDTO(Company company, Integer sessionUserCompanyId) {
            this.id = company.getId();
            this.nameKr = company.getNameKr();
            this.nameEn = company.getNameEn();
            this.ceo = company.getCeo();
            this.businessNumber = company.getBusinessNumber();
            this.email = company.getEmail();
            this.phone = company.getPhone();
            this.address = company.getAddress();
            this.introduction = company.getIntroduction();
            this.oneLineIntro = company.getOneLineIntro();
            this.homepageUrl = company.getHomepageUrl();
            this.logoImageUrl = company.getLogoImageUrl();
            this.infoImageUrl = company.getInfoImageUrl();
            this.contactManager = company.getContactManager();
            this.startDate = company.getStartDate();
            this.createdAt = company.getCreatedAt().toString().substring(0, 10);
            this.workField = new WorkFieldResponse.DTO(company.getWorkField());
            this.techStacks = company.getCompanyTechStacks().stream()
                    .map(companyTechStack -> new TechStackResponse.DTO(companyTechStack.getTechStack()))
                    .toList();
            this.isOwner = company.getId().equals(sessionUserCompanyId);
        }
    }

    @Data
    public static class ListDTO {
        private List<ItemDTO> companies;

        public ListDTO(List<Company> companyList) {
            this.companies = companyList.stream()
                    .map(company -> new ItemDTO(company))
                    .toList();
        }

        @Data
        class ItemDTO {
            private Integer id;
            private String nameKr;
            private String logoImageUrl;

            public ItemDTO(Company company) {
                this.id = company.getId();
                this.nameKr = company.getNameKr();
                this.logoImageUrl = company.getLogoImageUrl();
            }
        }
    }

    @Data
    public static class ApplicationListDTO {
        private Integer jobId;
        private String jobTitle;
        private List<ItemDTO> applications;
        private String status;

        public ApplicationListDTO(List<Application> applications, String status) {
            this.jobId = applications.getFirst().getJob().getId();
            this.jobTitle = applications.getFirst().getJob().getTitle();
            this.applications = applications.stream()
                    .map(application -> new ItemDTO(application))
                    .toList();
            this.status = status;
        }

        @Data
        class ItemDTO {
            private Integer applicationId;
            private String username;
            private String title;
            private String careerLevel;
            private String ApplyCreatedAt;

            public ItemDTO(Application application) {
                this.applicationId = application.getId();
                this.username = application.getUser().getUsername();
                this.title = application.getResume().getTitle();
                this.careerLevel = application.getResume().getCareerLevel();
                this.ApplyCreatedAt = application.getCreatedAt().toString().substring(0, 10);
            }
        }
    }

    @Data
    public static class ApplicationDetailDTO {
        private Integer applicationId;
        private String createdAt;
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
        private String username;
        private SalaryRangeResponse.DTO salaryRange;
        private JobGroupResponse.DTO jobGroup;
        private List<TechStackResponse.DTO> techStacks;

        public ApplicationDetailDTO(Application application) {
            this.applicationId = application.getId();
            this.createdAt = application.getCreatedAt().toString().substring(0, 10);
            this.resumeId = application.getResume().getId();
            this.title = application.getResume().getTitle();
            this.summary = application.getResume().getSummary();
            this.gender = application.getResume().getGender();
            this.careerLevel = application.getResume().getCareerLevel();
            this.education = application.getResume().getEducation();
            this.birthdate = application.getResume().getBirthdate();
            this.major = application.getResume().getMajor();
            this.graduationType = application.getResume().getGraduationType();
            this.phone = application.getResume().getPhone();
            this.portfolioUrl = application.getResume().getPortfolioUrl();
            this.enrollmentDate = application.getResume().getEnrollmentDate();
            this.graduationDate = application.getResume().getGraduationDate();
            this.username = application.getUser().getUsername();
            this.salaryRange = new SalaryRangeResponse.DTO(application.getResume().getSalaryRange());
            this.jobGroup = new JobGroupResponse.DTO(application.getResume().getJobGroup());
            this.techStacks = application.getResume().getResumeTechStacks().stream()
                    .map(resumeTechStack -> new TechStackResponse.DTO(resumeTechStack.getTechStack()))
                    .toList();
        }
    }
}
