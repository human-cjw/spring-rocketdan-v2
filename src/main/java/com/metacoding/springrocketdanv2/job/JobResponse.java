package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
<<<<<<< HEAD
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.workfield.WorkField;
=======
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.WorkFieldResponse;
>>>>>>> 394ec2d307f4964d5175ebdd4385e69cab668ca9
import lombok.Data;

import java.util.List;
import java.util.Optional;

public class JobResponse {

    @Data
    public static class ListDTO {
        private List<ItemDTO> jobs;

        public ListDTO(List<Job> jobs) {
            this.jobs = jobs.stream()
                    .map(job -> new ItemDTO(job))
                    .toList();
        }

        public class ItemDTO {
            private Integer id;
            private String title;
            private String careerLevel;
            private String nameKr;

            public ItemDTO(Job job) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
                this.nameKr = job.getCompany().getNameKr();
            }
        }
    }

    @Data
    public static class DetailDTO {
<<<<<<< HEAD
        private String title;
        private String deadline;
        private String careerLevel;
        private String createdAt;
        private String description;
        private String location;
        private String employmentType;
        private String workField;
        private String nameKr;
        private SalaryRangeDTO salaryRange;
        private Integer companyId;
        private Integer jobId;
        private String contactManager;
        private String companyPhone;
        private List<String> jobTechStacks = new ArrayList<>();
        private boolean isOwner;
        private Integer phone;

        private boolean Bookmarked;

        public DetailDTO(String title, String deadline, String careerLevel,
                         Timestamp createdAt, String description, String location,
                         String employmentType, String workField, String nameKr,
                         SalaryRange salaryRange, Integer companyId, Integer jobId,
                         String contactManager, String companyPhone, List<JobTechStack> jobTechStacks) {
            this.title = title;
            this.deadline = deadline;
            this.careerLevel = careerLevel;
            this.createdAt = createdAt.toString().substring(0, 10);
            this.description = description;
            this.location = location;
            this.employmentType = employmentType;
            this.workField = workField;
            this.nameKr = nameKr;
            this.salaryRange = new SalaryRangeDTO(salaryRange);
            this.companyId = companyId;
            this.jobId = jobId;
            this.contactManager = contactManager;
            this.companyPhone = companyPhone;

            for (JobTechStack jobTechStack : jobTechStacks) {
                this.jobTechStacks.add(jobTechStack.getTechStack().getName());
            }
        }

        public void setBookmarked(boolean Bookmarked) {
            this.Bookmarked = Bookmarked;
        }

        class SalaryRangeDTO {
            private String label;
            private String minSalary;
            private String maxSalary;

            public SalaryRangeDTO(SalaryRange salaryRange) {
                this.label = salaryRange.getLabel();
                this.minSalary = salaryRange.getMinSalary().toString();
                this.maxSalary = salaryRange.getMaxSalary().toString();
            }
        }
    }

    @Data
    public static class JobSaveDTO {
        private List<TechStack> techStacks;
        private List<WorkField> workFields;
        private List<SalaryRange> salaryRanges;
        private List<JobGroup> jobGroups;

        public JobSaveDTO(
                List<TechStack> techStacks,
                List<WorkField> workFields,
                List<SalaryRange> salaryRanges,
                List<JobGroup> jobGroups
        ) {
            this.techStacks = techStacks;
            this.workFields = workFields;
            this.salaryRanges = salaryRanges;
            this.jobGroups = jobGroups;
        }
    }

    @Data
    public static class JobUpdateDTO {
=======
>>>>>>> 394ec2d307f4964d5175ebdd4385e69cab668ca9
        private Integer id;
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private String careerLevel;
        private String createdAt;
        private String updatedAt;
        private CompanyDTO company;
        private SalaryRangeResponse.DTO salaryRange;
        private WorkFieldResponse.DTO workField;
        private JobGroupResponse.DTO jobGroup;
        private List<TechStackResponse.DTO> techStacks;
        private boolean isOwner;
        private boolean isBookmarked;

        public DetailDTO(Job job, User sessionUser, Optional<JobBookmark> jobBookmark) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.careerLevel = job.getCareerLevel();
            this.createdAt = job.getCreatedAt().toString().substring(0, 10);
            this.updatedAt = job.getUpdatedAt() != null ? job.getUpdatedAt().toString().substring(0, 10) : null;
            this.company = new CompanyDTO(job.getCompany());
            this.salaryRange = new SalaryRangeResponse.DTO(job.getSalaryRange());
            this.workField = new WorkFieldResponse.DTO(job.getWorkField());
            this.jobGroup = new JobGroupResponse.DTO(job.getJobGroup());
            this.techStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> new TechStackResponse.DTO(jobTechStack.getTechStack()))
                    .toList();
            this.isOwner = sessionUser.getCompanyId().equals(job.getCompany().getId());
            this.isBookmarked = jobBookmark.isPresent();
        }

        class CompanyDTO {
            private Integer id;
            private String name;
            private String phone;
            private String contactManager;

            public CompanyDTO(Company company) {
                this.id = company.getId();
                this.name = company.getNameKr();
                this.phone = company.getPhone();
                this.contactManager = company.getContactManager();
            }
        }
    }

    @Data
    public static class SaveDTO {
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private String jobGroup;
        private String workField;
        private String careerLevel;
        private String salaryRange;
        private List<String> techStacks;

        public SaveDTO(Job job) {
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.jobGroup = job.getJobGroup().getName();
            this.workField = job.getWorkField().getName();
            this.careerLevel = job.getCareerLevel();
            this.salaryRange = job.getSalaryRange().getLabel();
            this.techStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> jobTechStack.getTechStack().getName())
                    .toList();
        }
    }

    public static class UpdateDTO {
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private String jobGroup;
        private String workField;
        private String careerLevel;
        private String salaryRange;
        private List<String> techStacks;
    }
}