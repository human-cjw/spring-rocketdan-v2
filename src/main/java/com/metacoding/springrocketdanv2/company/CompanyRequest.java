package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStack;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CompanyRequest {

    @Data
    public static class SaveDTO {
        @NotBlank(message = "한글 이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "한글 이름은 2자 이상 20자 이하여야 합니다.")
        private String nameKr;

        @NotBlank(message = "영문 이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "영문 이름은 2자 이상 20자 이하여야 합니다.")
        private String nameEn;

        @NotBlank(message = "회사 소개는 필수입니다.")
        @Size(max = 1000, message = "회사 소개는 1000자 이내여야 합니다.")
        private String introduction;

        @NotBlank(message = "한 줄 소개는 필수입니다.")
        @Size(min = 2, max = 50, message = "한 줄 소개는 2자 이상 50자 이하여야 합니다.")
        private String oneLineIntro;

        @NotBlank(message = "설립일은 필수입니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "설립일은 yyyy-MM-dd 형식이어야 합니다.")
        private String startDate;

        @NotBlank(message = "사업자 등록번호는 필수입니다.")
        @Pattern(
                regexp = "^\\d{10}$",
                message = "사업자 등록번호는 10자리 숫자여야 합니다."
        )
        private String businessNumber;

        @NotEmpty(message = "기술 스택은 하나 이상 선택해야 합니다.")
        private List<@NotNull(message = "기술 스택 항목은 null일 수 없습니다.") Integer> techStackIds;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "홈페이지 URL은 필수입니다.")
        @Pattern(
                regexp = "^(https?://)?([\\w.-]+)+(:\\d+)?(/\\S*)?$",
                message = "올바른 URL 형식이 아닙니다."
        )
        private String homepageUrl;

        @NotBlank(message = "이름은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "이름은 2자 이상 30자 이하로 한글 또는 영문만 가능합니다.")
        private String contactManager;

        @NotBlank(message = "대표번호는 필수입니다.")
        @Pattern(regexp = "^\\d{3}\\d{4}\\d{4}$", message = "대표번호는 11자리 숫자여야 합니다.")
        private String phone; // 대표번호

        @NotBlank(message = "대표명은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "대표명은 2자 이상 30자 이하로 한글, 영문만 가능합니다.")
        private String ceo; // 대표명

        @NotBlank(message = "주소는 필수입니다.")
        @Size(max = 100, message = "주소는 100자 이내여야 합니다.")
        private String address;

        @NotNull(message = "업무 분야는 필수입니다.")
        private Integer workFieldId;

        public Company toEntity(Integer userId) {
            Company company = Company.builder()
                    .nameKr(nameKr)
                    .nameEn(nameEn)
                    .introduction(introduction)
                    .oneLineIntro(oneLineIntro)
                    .startDate(startDate)
                    .businessNumber(businessNumber)
                    .email(email)
                    .contactManager(contactManager)
                    .address(address)
                    .user(User.builder().id(userId).build())
                    .workField(WorkField.builder().id(workFieldId).build())
                    .phone(phone)
                    .ceo(ceo)
                    .homepageUrl(homepageUrl)
                    .build();

            for (Integer techStackId : techStackIds) {
                company.getCompanyTechStacks().add(
                        CompanyTechStack.builder()
                                .company(company)
                                .techStack(TechStack.builder().id(techStackId).build())
                                .build()
                );
            }
            return company;
        }
    }

    @Data
    public static class UpdateDTO {
        @NotBlank(message = "한글 이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "한글 이름은 2자 이상 20자 이하여야 합니다.")
        private String nameKr;

        @NotBlank(message = "영문 이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "영문 이름은 2자 이상 20자 이하여야 합니다.")
        private String nameEn;

        @NotBlank(message = "한 줄 소개는 필수입니다.")
        @Size(min = 2, max = 50, message = "한 줄 소개는 2자 이상 50자 이하여야 합니다.")
        private String oneLineIntro;

        @NotBlank(message = "회사 소개는 필수입니다.")
        @Size(max = 1000, message = "회사 소개는 1000자 이내여야 합니다.")
        private String introduction;

        @NotBlank(message = "설립일은 필수입니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "설립일은 yyyy-MM-dd 형식이어야 합니다.")
        private String startDate;

        @NotBlank(message = "사업자 등록번호는 필수입니다.")
        @Pattern(regexp = "^\\d{10}$", message = "사업자 등록번호는 10자리 숫자여야 합니다.")
        private String businessNumber;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "홈페이지 URL은 필수입니다.")
        @Pattern(
                regexp = "^(https?://)?([\\w.-]+)+(:\\d+)?(/\\S*)?$",
                message = "올바른 URL 형식이 아닙니다."
        )
        private String homepageUrl;

        @NotBlank(message = "담당자 이름은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "대표명은 2자 이상 30자 이하로 한글, 영문만 가능합니다.")
        private String contactManager; // 담당자 이름

        @NotBlank(message = "대표번호는 필수입니다.")
        @Pattern(regexp = "^\\d{3}\\d{4}\\d{4}$", message = "대표번호는 11자리 숫자여야 합니다.")
        private String phone; // 대표번호

        @NotBlank(message = "대표명은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "대표명은 2자 이상 30자 이하로 한글, 영문만 가능합니다.")
        private String ceo; // 대표명

        @NotBlank(message = "주소는 필수입니다.")
        @Size(max = 100, message = "주소는 100자 이내여야 합니다.")
        private String address;

        @NotNull(message = "업무 분야 선택은 필수입니다.")
        private Integer workFieldId;

        @NotEmpty(message = "기술 스택은 하나 이상 입력되어야 합니다.")
        private List<@NotNull(message = "기술 스택 항목은 비어 있을 수 없습니다.") Integer> techStackIds;
    }
}
