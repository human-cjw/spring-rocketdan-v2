package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.jobTechStack.JobTechStack;
import com.metacoding.springrocketdanv2.jobTechStack.JobTechStackResponse;
import com.metacoding.springrocketdanv2.jobgroup.Jobgroup;
import com.metacoding.springrocketdanv2.jobgroup.JobgroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.Salaryrange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryrangeResponse;
import com.metacoding.springrocketdanv2.techstack.Techstack;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.Workfield;
import com.metacoding.springrocketdanv2.workfield.WorkfieldResponse;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JobResponse {

    @Data
    public static class DTO {
        private int id;
        private String title;
        private String careerLevel;
        private String nameKr;
    }

    @Data
    public static class DetailDTO {
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
                         Salaryrange salaryRange, Integer companyId, Integer jobId,
                         String contactManager, String companyPhone, List<JobTechStack> jobTechStacks, UserResponse.SessionUserDTO sessionUserDTO) {
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
            if (sessionUserDTO != null) {
                this.isOwner = sessionUserDTO.getCompanyId() == null ? false : sessionUserDTO.getCompanyId().equals(companyId);
            }

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

            public SalaryRangeDTO(Salaryrange salaryRange) {
                this.label = salaryRange.getLabel();
                this.minSalary = salaryRange.getMinSalary().toString();
                this.maxSalary = salaryRange.getMaxSalary().toString();
            }
        }
    }

    @Data
    public static class JobSaveDTO {
        private List<Techstack> techstacks;
        private List<Workfield> workFields;
        private List<Salaryrange> salaryranges;
        private List<Jobgroup> jobgroups;

        public JobSaveDTO(
                List<Techstack> techstacks,
                List<Workfield> workFields,
                List<Salaryrange> salaryranges,
                List<Jobgroup> jobgroups
        ) {
            this.techstacks = techstacks;
            this.workFields = workFields;
            this.salaryranges = salaryranges;
            this.jobgroups = jobgroups;
        }
    }

    @Data
    public static class JobUpdateDTO {
        private Integer id;
        private String title;
        private String description;
        private String location;
        private String deadline;
        private String status;

        private List<CareerLevel> careerLevels;
        private List<EmploymentType> employmentTypes;
        private List<JobTechStackResponse.JobTechStackUpdateDTO> jobTechStackUpdateDTOs;
        private List<WorkfieldResponse.WorkFieldUpdateDTO> workFieldUpdateDTOs;
        private List<SalaryrangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs;
        private List<JobgroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs;

        public JobUpdateDTO(
                Integer id,
                String title,
                String description,
                String location,
                String deadline,
                String status,
                List<CareerLevel> careerLevels,
                List<EmploymentType> employmentTypes,
                List<JobTechStackResponse.JobTechStackUpdateDTO> jobTechStackUpdateDTOs,
                List<WorkfieldResponse.WorkFieldUpdateDTO> workFieldUpdateDTOs,
                List<SalaryrangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs,
                List<JobgroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs
        ) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.location = location;
            this.deadline = deadline;
            this.status = status;
            this.careerLevels = careerLevels;
            this.employmentTypes = employmentTypes;
            this.jobTechStackUpdateDTOs = jobTechStackUpdateDTOs;
            this.workFieldUpdateDTOs = workFieldUpdateDTOs;
            this.salaryRangeUpdateDTOs = salaryRangeUpdateDTOs;
            this.jobGroupUpdateDTOs = jobGroupUpdateDTOs;
        }

        public static class EmploymentType {
            private String value;
            private String name;
            private boolean isSelected;

            public EmploymentType(
                    String value,
                    String name,
                    boolean isSelected
            ) {
                this.value = value;
                this.name = name;
                this.isSelected = isSelected;
            }
        }

        public static class CareerLevel {
            private String value;
            private String name;
            private boolean isSelected;

            public CareerLevel(
                    String value,
                    String name,
                    boolean isSelected
            ) {
                this.value = value;
                this.name = name;
                this.isSelected = isSelected;
            }
        }
    }
}