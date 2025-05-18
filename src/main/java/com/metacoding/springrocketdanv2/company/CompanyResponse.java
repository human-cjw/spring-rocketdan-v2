package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyResponse {

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
        private String workFieldName; // ManyToOne → 이름만
        private List<Integer> techStackIds; // OneToMany → 이름만
        private boolean isOwner;

        public DetailDTO(Company company, UserResponse.DTO sessionUser) {
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
            this.createdAt = company.getCreatedAt().toString();
            this.workFieldName = company.getWorkField() != null ? company.getWorkField().getName() : null;
            this.techStackIds = company.getCompanyTechStacks() == null
                    ? null
                    : company.getCompanyTechStacks().stream()
                    .map(cts -> cts.getTechStack().getId())
                    .toList();
            this.isOwner = sessionUser != null &&
                    "company".equals(sessionUser.getUserType()) &&
                    sessionUser.getCompanyId() != null &&
                    sessionUser.getCompanyId().equals(company.getId());
        }
    }

    @Data
    public static class ListDTO {
        private List<ItemDTO> companies;

        public ListDTO(List<Company> companyList, UserResponse.DTO sessionUser) {
            this.companies = companyList.stream()
                    .map(company -> new ItemDTO(company, sessionUser))
                    .toList();
        }
    }

    @Data
    public static class ItemDTO {
        private Integer id;
        private String nameKr;
        private String logoImageUrl;
        private boolean isOwner;

        public ItemDTO(Company company, UserResponse.DTO sessionUser) {
            this.id = company.getId();
            this.nameKr = company.getNameKr();
            this.logoImageUrl = company.getLogoImageUrl();
            this.isOwner = sessionUser != null &&
                    "company".equals(sessionUser.getUserType()) &&
                    sessionUser.getCompanyId() != null &&
                    sessionUser.getCompanyId().equals(company.getId());
        }
    }

    @Data
    public static class JobListDTO {
        private List<JobItemDTO> jobs;

        public JobListDTO(List<Job> jobList) {
            this.jobs = jobList.stream()
                    .map(job -> new JobItemDTO(job))
                    .toList();
        }

        @Data
        public static class JobItemDTO {
            private Integer id;
            private String title;
            private String careerLevel;
            private String createdAt;
            private String jobGroupName;

            public JobItemDTO(Job job) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
                this.createdAt = job.getCreatedAt()
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                this.jobGroupName = job.getJobGroup().getName();
            }
        }
    }

    @Data
    public static class ResumeListDTO {
        private Integer jobId;
        private String jobTitle;
        private List<ResumeItemDTO> applications;

        public ResumeListDTO(Integer jobId, String jobTitle, List<ResumeItemDTO> applications) {
            this.jobId = jobId;
            this.jobTitle = jobTitle;
            this.applications = applications;
        }

        @Data
        public static class ResumeItemDTO {
            private Integer applicationId;
            private String username;
            private String resumeTitle;
            private String careerLevel;
            private String createdAt;
            private String status;
            private boolean isAccepted;
            private boolean isRejected;
            private boolean isPending;

            public ResumeItemDTO(Integer applicationId, String username, String resumeTitle,
                                 String careerLevel, LocalDateTime createdAt, String status) {
                this.applicationId = applicationId;
                this.username = username;
                this.resumeTitle = resumeTitle;
                this.careerLevel = careerLevel;
                this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                this.status = status;
                this.isAccepted = "합격".equals(status);
                this.isRejected = "불합격".equals(status);
                this.isPending = "접수".equals(status) || "검토".equals(status);
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
