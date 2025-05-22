package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.job.techstack.JobStatusEnum;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStack;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

public class JobRequest {

    @Data
    public static class SaveDTO {
        @NotBlank(message = "제목은 필수입니다.")
        @Pattern(
                regexp = "^.{2,30}$",
                message = "제목은 2자 이상 30자 이하로 입력해 주세요."
        )
        private String title;

        @NotBlank(message = "설명은 필수입니다.")
        @Pattern(
                regexp = "^.{2,1000}$",
                message = "설명은 2자 이상 1000자 이하로 입력해 주세요."
        )
        private String description;

        @NotBlank(message = "주소는 필수입니다.")
        @Pattern(
                regexp = "^[가-힣]{2,20}\\s[가-힣]{1,10}\\s[가-힣0-9]{1,20}$",
                message = "주소는 '서울특별시 구로구 디지털로'와 같이 세 단어로 입력해 주세요."
        )
        private String location;

        @NotBlank(message = "고용 형태는 필수입니다.")
        @Pattern(
                regexp = "^(정규직|계약직|인턴|프리랜서)$",
                message = "정규직, 계약직, 인턴, 프리랜서 중 하나를 선택해 주세요."
        )
        private String employmentType;

        @NotBlank(message = "마감일은 필수입니다.")
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "마감일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String deadline;

        @NotNull(message = "직무 그룹 선택은 필수입니다.")
        private Integer jobGroupId;

        @NotNull(message = "업무 분야 선택은 필수입니다.")
        private Integer workFieldId;

        @NotBlank(message = "경력 수준은 필수입니다.")
        @Pattern(
                regexp = "^(경력|신입)$",
                message = "경력 수준은 경력 또는 신입으로 입력해 주세요."
        )
        private String careerLevel;

        @NotNull(message = "급여 범위 선택은 필수입니다.")
        private Integer salaryRangeId;

        @NotEmpty(message = "기술 스택 ID는 하나 이상 선택해야 합니다.")
        private List<@NotNull(message = "기술 스택 ID는 null일 수 없습니다.") Integer> techStackIds;

        public Job toEntity(Integer companyId) {
            Job job = Job.builder()
                    .title(title)
                    .description(description)
                    .location(location)
                    .employmentType(employmentType)
                    .deadline(deadline)
                    .status(JobStatusEnum.OPEN.value)
                    .careerLevel(careerLevel)
                    .jobGroup(JobGroup.builder()
                            .id(jobGroupId)
                            .build())
                    .company(Company.builder()
                            .id(companyId)
                            .build())
                    .workField(WorkField.builder()
                            .id(workFieldId)
                            .build())
                    .salaryRange(SalaryRange.builder()
                            .id(salaryRangeId)
                            .build())
                    .build();

            for (Integer techStackId : techStackIds) {
                job.getJobTechStacks().add(
                        JobTechStack.builder()
                                .job(job)
                                .techStack(TechStack.builder()
                                        .id(techStackId)
                                        .build()
                                ).build()
                );
            }

            return job;
        }
    }

    @Data
    public static class UpdateDTO {

        @NotBlank(message = "제목은 필수입니다.")
        @Pattern(
                regexp = "^.{2,30}$",
                message = "제목은 2자 이상 30자 이하로 입력해 주세요."
        )
        private String title;

        @NotBlank(message = "설명은 필수입니다.")
        @Pattern(
                regexp = "^.{2,1000}$",
                message = "설명은 2자 이상 1000자 이하로 입력해 주세요."
        )
        private String description;

        @NotBlank(message = "주소는 필수입니다.")
        @Pattern(
                regexp = "^[가-힣]{2,20}\\s[가-힣]{1,10}\\s[가-힣0-9]{1,20}$",
                message = "주소는 '서울특별시 구로구 디지털로'와 같이 세 단어로 입력해 주세요."
        )
        private String location;

        @NotBlank(message = "고용 형태는 필수입니다.")
        @Pattern(
                regexp = "^(정규직|계약직|인턴|프리랜서)$",
                message = "고용 형태는 정규직, 계약직, 인턴, 프리랜서 중 하나여야 합니다."
        )
        private String employmentType;

        @NotBlank(message = "마감일은 필수입니다.")
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "마감일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String deadline;

        @NotBlank(message = "공고 상태는 필수입니다.")
        @Pattern(
                regexp = "^(OPEN|CLOSED)$",
                message = "공고 상태는 OPEN 또는 CLOSED여야 합니다."
        )
        private String status;

        @NotNull(message = "직무 그룹 선택은 필수입니다.")
        private Integer jobGroupId;

        @NotNull(message = "업무 분야 선택은 필수입니다.")
        private Integer workFieldId;

        @NotBlank(message = "경력 수준은 필수입니다.")
        @Pattern(
                regexp = "^(경력|신입)$",
                message = "경력 수준은 경력 또는 신입이어야 합니다."
        )
        private String careerLevel;

        @NotNull(message = "급여 범위 선택은 필수입니다.")
        private Integer salaryRangeId;

        @NotEmpty(message = "기술 스택 목록은 하나 이상 포함해야 합니다.")
        private List<@NotNull(message = "기술 스택 ID는 null일 수 없습니다.") Integer> techStackIds;
    }
}
