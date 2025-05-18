package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ApplicationResponse {

    @Data
    public static class ProcessDTO2 {
        private Integer jobId;
        private String nameEn;
        private String nameKr;
        private String jobTitle;
        private String jobCareerLevel;
        List<StateDTO> states;

        public ProcessDTO2(Application application) {
            this.jobId = application.getJob().getId();
            this.nameEn = application.getJob().getCompany().getNameEn();
            this.nameKr = application.getJob().getCompany().getNameKr();
            this.jobTitle = application.getJob().getTitle();
            this.jobCareerLevel = application.getJob().getCareerLevel();
            this.states = List.of(
                    new StateDTO("접수", application.getStatus().equals("접수")),
                    new StateDTO("검토", application.getStatus().equals("검토")),
                    new StateDTO("합격", application.getStatus().equals("합격")),
                    new StateDTO("불합격", application.getStatus().equals("불합격"))
            );
        }

        class StateDTO {
            private String status; // 접수, 검토, 합격, 불합격
            private boolean isChecked;

            public StateDTO(String status, boolean isChecked) {
                this.status = status;
                this.isChecked = isChecked;
            }
        }
    }

    @Data
    public static class ProcessDTO {
        private Integer id;
        private String status;
        private String createdAt;
        private JobDTO job;
        private ResumeDTO resume;
        private CompanyDTO company;
        private UserDTO user;

        public ProcessDTO(Application application) {
            this.id = application.getId();
            this.status = application.getStatus();
            this.createdAt = application.getCreatedAt().toString().substring(0, 16);
            this.job = new JobDTO(application.getJob());
            this.resume = new ResumeDTO(application.getResume());
            this.company = new CompanyDTO(application.getCompany());
            this.user = new UserDTO(application.getUser());
        }

        @Data
        public static class JobDTO {
            private Integer id;
            private String title;
            private String careerLevel;

            public JobDTO(Job job) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
            }
        }

        @Data
        public static class ResumeDTO {
            private Integer id;
            private String title;

            public ResumeDTO(Resume resume) {
                this.id = resume.getId();
                this.title = resume.getTitle();
            }
        }

        @Data
        public static class CompanyDTO {
            private Integer id;
            private String nameKr;
            private String nameEn;

            public CompanyDTO(Company company) {
                this.id = company.getId();
                this.nameKr = company.getNameKr();
                this.nameEn = company.getNameEn();
            }
        }

        @Data
        public static class UserDTO {
            private Integer id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }
    }

    @Data
    public static class ApplicationManageDTO {
        private Integer id;
        private String status; // 지원 상태. 접수, 검토, 합격, 탈락
        private String createdAt;
        private JobDTO job;
        private ResumeDTO resume;
        private CompanyDTO company;
        private UserDTO user;


        public ApplicationManageDTO(Integer id, String status, String createdAt, JobDTO job, ResumeDTO resume, CompanyDTO company, UserDTO user) {
            this.id = id;
            this.status = status;
            this.createdAt = createdAt;
            this.job = job;
            this.resume = resume;
            this.company = company;
            this.user = user;
        }

        @Data
        public static class JobDTO {
            private Integer id;
            private String title;
            private String careerLevel;

            public JobDTO(Job job) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
            }
        }

        @Data
        public static class ResumeDTO {
            private Integer id;
            private String title;

            public ResumeDTO(Resume resume) {
                this.id = resume.getId();
                this.title = resume.getTitle();
            }
        }

        @Data
        public static class CompanyDTO {
            private Integer id;
            private String nameKr;

            public CompanyDTO(Company company) {
                this.id = company.getId();
                this.nameKr = company.getNameKr();
            }
        }

        @Data
        public static class UserDTO {
            private Integer id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }
    }

    @Data
    public static class ApplyDTO {
        private Integer jobId;
        private String jobTitle;
        private String username;
        private Integer companyId;
        private List<ResumeDTO> resumes = new ArrayList<>();

        public ApplyDTO(Job job, String username, List<Resume> resumes) {
            this.jobId = job.getId();
            this.jobTitle = job.getTitle();
            this.companyId = job.getCompany().getId();
            this.username = username;

            for (Resume resume : resumes) {
                this.resumes.add(new ResumeDTO(resume.getId(), resume.getTitle(), resume.getIsDefault()));
            }
        }

        public static class ResumeDTO {
            private Integer resumeId;
            private String title;
            private boolean isChecked; // 기본이력서를 체크

            public ResumeDTO(Integer resumeId, String title, boolean isChecked) {
                this.resumeId = resumeId;
                this.title = title;
                this.isChecked = isChecked;
            }
        }
    }

    @Data
    public static class ApplyDoneDTO {
        private String title;
        private String deadline;
        private String employmentType;
        private String careerLevel;
        private String updatedAt;
        private String description;

        public ApplyDoneDTO(Job job) {
            this.title = job.getTitle();
            this.deadline = job.getDeadline();
            this.employmentType = job.getEmploymentType();
            this.careerLevel = job.getCareerLevel();
            this.updatedAt = job.getUpdatedAt() != null ? job.getUpdatedAt().toString().substring(0, 10) : null;
            this.description = job.getDescription();
        }
    }

    @Data
    public static class AcceptanceDTO {
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

        private List<CareerDTO> careers;
        private List<TechStackDTO> techStacks;

        public AcceptanceDTO(Resume resume, List<Career> careers, List<TechStack> techStacks, Integer applicationId) {
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
            this.careers = careers.stream()
                    .map(career -> new CareerDTO(career.getCompanyName()))
                    .toList();
            this.techStacks = techStacks.stream()
                    .map(tech -> new TechStackDTO(tech.getName()))
                    .toList();
        }

        @Data
        public static class CareerDTO {
            private String companyName;

            public CareerDTO(String companyName) {
                this.companyName = companyName;
            }
        }

        @Data
        public static class TechStackDTO {
            private String name;

            public TechStackDTO(String name) {
                this.name = name;
            }
        }
    }
}
