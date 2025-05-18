package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.workfield.WorkFieldResponse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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
        private WorkFieldResponse.DTO workField;
        private List<TechStackResponse.DTO> techStacks;

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
            this.workField = new WorkFieldResponse.DTO(company.getWorkField());
            this.techStacks = company.getCompanyTechStacks().stream()
                    .map(companyTechStack -> new TechStackResponse.DTO(companyTechStack.getTechStack()))
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
        private WorkFieldResponse.DTO workField;
        private List<TechStackResponse.DTO> techStacks;

        public SaveDTO(Company company) {
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
            this.workField = new WorkFieldResponse.DTO(company.getWorkField());
            this.techStacks = company.getCompanyTechStacks().stream()
                    .map(companyTechStack -> new TechStackResponse.DTO(companyTechStack.getTechStack()))
                    .toList();
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

        public ApplicationListDTO(List<Application> applications) {
            this.jobId = applications.getFirst().getJob().getId();
            this.jobTitle = applications.getFirst().getJob().getTitle();
            this.applications = applications.stream()
                    .map(application -> new ItemDTO(application))
                    .toList();
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

    //---------------------------------여기까지 완료 ------------------------------------------------------------
    @Data
    public static class CompanyacceptanceDTO {
        private Integer applicationId;
        private String username;
        private String resumeTitle;
        private String summary;
        private String gender;
        private String careerLevel;
        private String education;
        private String birthdate;
        private String major;
        private String graduationType;
        private String phone;
        private String portfolioUrl;

        private List<String> careerCompanyNames; // 커리어 회사명 리스트
        private List<String> techStackNames; // 기술스택 이름 리스트

        public CompanyacceptanceDTO(Resume resume, List<Career> careers, List<TechStack> techStacks, Integer applicationId) {
            this.applicationId = applicationId;
            this.username = resume.getUser().getUsername();
            this.resumeTitle = resume.getTitle();
            this.summary = resume.getSummary();
            this.gender = resume.getGender();
            this.careerLevel = resume.getCareerLevel();
            this.education = resume.getEducation();
            this.birthdate = resume.getBirthdate();
            this.major = resume.getMajor();
            this.graduationType = resume.getGraduationType();
            this.phone = resume.getPhone();
            this.portfolioUrl = resume.getPortfolioUrl();

            // 커리어 회사명만 뽑아오기
            this.careerCompanyNames = careers.stream()
                    .map(Career::getCompanyName)
                    .collect(Collectors.toList());

            // 기술스택 이름만 뽑아오기
            this.techStackNames = techStacks.stream()
                    .map(TechStack::getName)
                    .collect(Collectors.toList());
        }
    }
}
