package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackResponse;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.WorkFieldResponse;
import lombok.Data;

import java.util.List;

public class JobResponse {

    @Data
    public static class ListDTO {
        private List<ItemDTO> jobs;
        private Integer bookmarkCount;

        public ListDTO(List<Job> jobs, List<JobBookmark> jobBookmarks) {
            if (jobBookmarks.isEmpty()) {
                this.jobs = jobs.stream()
                        .map(job -> new ItemDTO(job, null))
                        .toList();
            } else {
                this.jobs = jobs.stream()
                        .map(job -> {
                            Integer bookmarkId = null;
                            for (JobBookmark jobBookmark : jobBookmarks) {
                                if (jobBookmark.getJob().getId().equals(job.getId())) {
                                    bookmarkId = jobBookmark.getId();
                                    break;
                                }
                            }
                            return new ItemDTO(job, bookmarkId);
                        })
                        .toList();
            }

            this.bookmarkCount = jobBookmarks.size();
        }

        @Data
        public class ItemDTO {
            private Integer id;
            private String title;
            private String careerLevel;
            private String companyName;
            private Integer bookmarkId;

            public ItemDTO(Job job, Integer bookmarkId) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
                this.companyName = job.getCompany().getNameKr();
                this.bookmarkId = bookmarkId;
            }
        }
    }

    @Data
    public static class DetailDTO {
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
        private Integer jobBookmarkId;

        public DetailDTO(Job job, User sessionUser, Integer jobBookmarkId) {
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
            this.isOwner = sessionUser != null && sessionUser.getCompanyId() != null && sessionUser.getCompanyId().equals(job.getCompany().getId());
            this.jobBookmarkId = jobBookmarkId;
        }

        @Data
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
        private Integer id;
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private Integer jobGroupId;
        private Integer workFieldId;
        private String careerLevel;
        private Integer salaryRangeId;
        private List<JobTechStackResponse.DTO> jobTechStacks;

        public SaveDTO(Job job) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.jobGroupId = job.getJobGroup().getId();
            this.workFieldId = job.getWorkField().getId();
            this.careerLevel = job.getCareerLevel();
            this.salaryRangeId = job.getSalaryRange().getId();
            this.jobTechStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> new JobTechStackResponse.DTO(jobTechStack))
                    .toList();
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private Integer jobGroupId;
        private Integer workFieldId;
        private String careerLevel;
        private Integer salaryRangeId;
        private List<JobTechStackResponse.DTO> jobTechStacks;

        public UpdateDTO(Job job) {
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.jobGroupId = job.getJobGroup().getId();
            this.workFieldId = job.getWorkField().getId();
            this.careerLevel = job.getCareerLevel();
            this.salaryRangeId = job.getSalaryRange().getId();
            this.jobTechStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> new JobTechStackResponse.DTO(jobTechStack))
                    .toList();
        }
    }

    @Data
    public static class DTO {
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
        private Integer companyId;
        private String companyName;
        private SalaryRangeResponse.DTO salaryRange;
        private WorkFieldResponse.DTO workField;
        private JobGroupResponse.DTO jobGroup;

        public DTO(Job job) {
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
            this.companyId = job.getCompany().getId();
            this.companyName = job.getCompany().getNameKr();
            this.salaryRange = new SalaryRangeResponse.DTO(job.getSalaryRange());
            this.workField = new WorkFieldResponse.DTO(job.getWorkField());
            this.jobGroup = new JobGroupResponse.DTO(job.getJobGroup());
        }
    }
}