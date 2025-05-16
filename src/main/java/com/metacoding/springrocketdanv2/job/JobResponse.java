package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStackResponse;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.WorkFieldResponse;
import lombok.Data;

import java.util.List;
import java.util.Optional;

public class JobResponse {

    @Data
    public static class ListDTO {
        private List<ItemDTO> jobs;

        public ListDTO(List<Job> jobs, List<JobBookmark> jobBookmarks) {
            if (jobBookmarks.size() == 0) {
                this.jobs = jobs.stream()
                        .map(job -> {
                            return new ItemDTO(job, false);
                        })
                        .toList();
            }
            List<Integer> jobBookmarkJobIds = jobBookmarks.stream()
                    .map(jobBookmark -> jobBookmark.getJob().getId())
                    .toList();

            this.jobs = jobs.stream()
                    .map(job -> {
                        boolean isBookmarked = jobBookmarkJobIds.contains(job.getId());

                        return new ItemDTO(job, isBookmarked);
                    })
                    .toList();
        }

        @Data
        public class ItemDTO {
            private Integer id;
            private String title;
            private String careerLevel;
            private String companyName;
            private boolean isBookmarked;

            public ItemDTO(Job job, boolean isBookmarked) {
                this.id = job.getId();
                this.title = job.getTitle();
                this.careerLevel = job.getCareerLevel();
                this.companyName = job.getCompany().getNameKr();
                this.isBookmarked = isBookmarked;
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
            this.isOwner = sessionUser.getCompanyId() != null && sessionUser.getCompanyId().equals(job.getCompany().getId());
            this.isBookmarked = jobBookmark.isPresent();
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
        private JobGroupResponse.DTO jobGroup;
        private WorkFieldResponse.DTO workField;
        private String careerLevel;
        private SalaryRangeResponse.DTO salaryRange;
        private List<TechStackResponse.DTO> techStacks;

        public SaveDTO(Job job) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.jobGroup = new JobGroupResponse.DTO(job.getJobGroup());
            this.workField = new WorkFieldResponse.DTO(job.getWorkField());
            this.careerLevel = job.getCareerLevel();
            this.salaryRange = new SalaryRangeResponse.DTO(job.getSalaryRange());
            this.techStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> new TechStackResponse.DTO(jobTechStack.getTechStack()))
                    .toList();
        }
    }

    @Data
    public static class UpdateDTO {
        private Integer id;
        private String title;
        private String description;
        private String location;
        private String employmentType;
        private String deadline;
        private String status;
        private JobGroupResponse.DTO jobGroup;
        private WorkFieldResponse.DTO workField;
        private String careerLevel;
        private SalaryRangeResponse.DTO salaryRange;
        private List<TechStackResponse.DTO> techStacks;

        public UpdateDTO(Job job) {
            this.id = job.getId();
            this.title = job.getTitle();
            this.description = job.getDescription();
            this.location = job.getLocation();
            this.employmentType = job.getEmploymentType();
            this.deadline = job.getDeadline();
            this.status = job.getStatus();
            this.jobGroup = new JobGroupResponse.DTO(job.getJobGroup());
            this.workField = new WorkFieldResponse.DTO(job.getWorkField());
            this.careerLevel = job.getCareerLevel();
            this.salaryRange = new SalaryRangeResponse.DTO(job.getSalaryRange());
            this.techStacks = job.getJobTechStacks().stream()
                    .map(jobTechStack -> new TechStackResponse.DTO(jobTechStack.getTechStack()))
                    .toList();
        }
    }
}