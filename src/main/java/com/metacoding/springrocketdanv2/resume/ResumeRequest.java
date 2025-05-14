package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStack;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

public class ResumeRequest {

    @Data
    public static class UpdateDTO {

        @NotBlank(message = "제목은 필수입니다.")
        @Pattern(
                regexp = "^.{2,30}$",
                message = "제목은 2자 이상 30자 이하로 입력해 주세요."
        )
        private String title;

        @NotBlank(message = "자기소개는 필수입니다.")
        @Pattern(
                regexp = "^.{2,1000}$",
                message = "설명은 2자 이상 1000자 이하로 입력해 주세요."
        )
        private String summary;

        @Size(max = 255, message = "포트폴리오 주소는 최대 255 입력 가능합니다.")
        private String portfolioUrl;

        @Pattern(
                regexp = "^(남|여)$",
                message = "성별은 '남' 또는 '여'로 입력해 주세요."
        )
        private String gender;

        @Size(max = 100, message = "학력사항은 최대 100자까지 입력 가능합니다.")
        private String education;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "생년월일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String birthdate;

        @Pattern(
                regexp = "^[가-힣]{2,20}$",
                message = "전공은 한글 2자 이상 20자 이하로 입력해 주세요."
        )
        private String major;

        @Pattern(
                regexp = "^(졸업|재학|휴학|졸업예정)$",
                message = "졸업형태는 졸업, 재학, 휴학, 졸업예정 중 하나로 입력해 주세요."
        )
        private String graduationType;

        @Pattern(
                regexp = "^0\\d{9,10}$",
                message = "연락처는 하이픈 없이 숫자만 입력해 주세요. 예: 01012345678"
        )
        private String phone;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "입학 날짜는 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String enrollmentDate;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "졸업 날짜는 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String graduationDate;

        @Pattern(
                regexp = "^(경력|신입)$",
                message = "경력 수준은 경력 또는 신입 중 하나로 입력해 주세요."
        )
        private String careerLevel;

        private Boolean isDefault;

        @Pattern(
                regexp = "^[가-힣]{2,20}$",
                message = "회사명은 한글 2자 이상 20자 이하로 입력해 주세요."
        )
        private String careerCompanyName;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "경력 시작일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String careerStartDate;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "경력 종료일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String careerEndDate;

        @Pattern(
                regexp = "^[가-힣]{2,50}$",
                message = "자격증명은 한글 2자 이상 50자 이하로 입력해 주세요."
        )
        private String certificationName;

        @Pattern(
                regexp = "^[가-힣]{2,50}$",
                message = "발급기관명은 한글 2자 이상 50자 이하로 입력해 주세요."
        )
        private String certificationIssuer;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "발급일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String certificationIssuedDate;

        private Integer salaryRangeId;

        private Integer jobGroupId;

        @NotEmpty(message = "기술 스택을 하나 이상 선택해 주세요.")
        private List<@NotNull(message = "기술 스택 ID는 null일 수 없습니다.") Integer> techStackIds;
    }

    @Data
    public static class SaveDTO {
        @NotBlank(message = "제목은 필수입니다.")
        @Pattern(
                regexp = "^.{2,30}$",
                message = "제목은 2자 이상 30자 이하로 입력해 주세요."
        )
        private String title;

        @NotBlank(message = "자기소개는 필수입니다.")
        @Pattern(
                regexp = "^.{2,1000}$",
                message = "설명은 2자 이상 1000자 이하로 입력해 주세요."
        )
        private String summary;

        @Size(max = 255, message = "포트폴리오 주소는 최대 255자 입력 가능합니다.")
        private String portfolioUrl;

        @Pattern(
                regexp = "^(남|여)$",
                message = "성별은 '남' 또는 '여'로 입력해 주세요."
        )
        private String gender;

        @Size(max = 100, message = "학력사항은 최대 100자까지 입력 가능합니다.")
        private String education;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "생년월일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String birthdate;

        @Pattern(
                regexp = "^[가-힣]{2,20}$",
                message = "전공은 한글 2자 이상 20자 이하로 입력해 주세요."
        )
        private String major;

        @Pattern(
                regexp = "^(졸업|재학|휴학|졸업예정)$",
                message = "졸업형태는 졸업, 재학, 휴학, 졸업예정 중 하나로 입력해 주세요."
        )
        private String graduationType;

        @Pattern(
                regexp = "^0\\d{9,10}$",
                message = "연락처는 하이픈 없이 숫자만 입력해 주세요. 예: 01012345678"
        )
        private String phone;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "입학 날짜는 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String enrollmentDate;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "졸업 날짜는 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String graduationDate;

        @Pattern(
                regexp = "^(경력|신입)$",
                message = "경력 수준은 경력 또는 신입 중 하나로 입력해 주세요."
        )
        private String careerLevel;

        private Boolean isDefault;

        @Pattern(
                regexp = "^[가-힣]{2,20}$",
                message = "회사명은 한글 2자 이상 20자 이하로 입력해 주세요."
        )
        private String careerCompanyName;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "경력 시작일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String careerStartDate;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "경력 종료일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String careerEndDate;

        @Pattern(
                regexp = "^[가-힣]{2,50}$",
                message = "자격증명은 한글 2자 이상 50자 이하로 입력해 주세요."
        )
        private String certificationName;

        @Pattern(
                regexp = "^[가-힣]{2,50}$",
                message = "발급기관명은 한글 2자 이상 50자 이하로 입력해 주세요."
        )
        private String certificationIssuer;

        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "발급일은 yyyy-MM-dd 형식으로 입력해 주세요."
        )
        private String certificationIssuedDate;

        @NotNull(message = "연봉 범위 선택은 필수입니다.")
        private Integer salaryRangeId;

        @NotNull(message = "직무 그룹 선택은 필수입니다.")
        private Integer jobGroupId;

        @NotEmpty(message = "기술 스택을 하나 이상 선택해 주세요.")
        private List<@NotNull(message = "기술 스택 ID는 null일 수 없습니다.") Integer> techStackIds;

        public Resume toEntity(Integer userId) {
            Resume resume = Resume.builder()
                    .user(User.builder().id(userId).build())
                    .title(title)
                    .summary(summary)
                    .portfolioUrl(portfolioUrl)
                    .gender(gender)
                    .education(education)
                    .birthdate(birthdate)
                    .major(major)
                    .graduationType(graduationType)
                    .phone(phone)
                    .enrollmentDate(enrollmentDate)
                    .graduationDate(graduationDate)
                    .careerLevel(careerLevel)
                    .isDefault(isDefault)
                    .salaryRange(SalaryRange.builder().id(salaryRangeId).build())
                    .jobGroup(JobGroup.builder().id(jobGroupId).build())
                    .build();

            resume.getResumeTechStacks().clear();
            for (Integer techStackId : techStackIds) {
                resume.getResumeTechStacks().add(
                        ResumeTechStack.builder()
                                .resume(resume)
                                .techStack(TechStack.builder()
                                        .id(techStackId)
                                        .build()
                                ).build()
                );
            }

            return resume;
        }
    }
}
